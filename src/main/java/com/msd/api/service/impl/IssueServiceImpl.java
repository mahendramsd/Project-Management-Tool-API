package com.msd.api.service.impl;

import com.msd.api.domain.*;
import com.msd.api.dto.ColumnsDto;
import com.msd.api.dto.IssueDto;
import com.msd.api.dto.request.AssignRequest;
import com.msd.api.dto.request.IssueRequest;
import com.msd.api.dto.request.IssueStateRequest;
import com.msd.api.dto.response.BoardResponse;
import com.msd.api.dto.response.ChangeLogResponse;
import com.msd.api.dto.response.IssueResponse;
import com.msd.api.exception.CustomException;
import com.msd.api.repositories.*;
import com.msd.api.service.IssueService;
import com.msd.api.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {

    private static final Logger logger = LoggerFactory.getLogger(IssueServiceImpl.class);

    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;
    private final ChangeLogRepository changeLogRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public IssueServiceImpl(ProjectRepository projectRepository, IssueRepository issueRepository, ChangeLogRepository changeLogRepository, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.changeLogRepository = changeLogRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void createIssue(IssueRequest issueRequest, Long userId) {
        Optional<Project> project = projectRepository.findById(issueRequest.getProjectId());
        if (project.isPresent()) {
            if (logger.isDebugEnabled())
                logger.debug("new Issue initiated");
            Issue issue = new Issue();
            issue.setTitle(issueRequest.getTitle());
            issue.setType(IssueType.getIssueType(issueRequest.getIssueType()));
            issue.setCurrentState(State.getState(issueRequest.getFromState()));
            issue.setStatus(Status.ACTIVE);
            issue.setProject(project.get());

            User assignee = userRepository.findById(issueRequest.getUserId()).orElseThrow(() ->
                    new CustomException(CustomErrorCodes.USER_NOT_FOUND));
            issue.setAssignee(assignee);
            issueRepository.save(issue);
            issue.setIssueId(project.get().getCode().concat("-").concat(issue.getId().toString()));
            User assigner = userRepository.findById(userId).orElseThrow(() ->
                    new CustomException(CustomErrorCodes.USER_NOT_FOUND));
            List<ChangeLog> changeLogs = new ArrayList<>();
            ChangeLog changeLogCreate = updateChangeLog(issue, State.CREATED, State.OPEN, assigner, assignee, assignee, ChangeLogType.CREATE);
            changeLogs.add(changeLogCreate);
            issue.setChangeLogs(changeLogs);
            if (assignee.getId() != 0) {
                ChangeLog changeLogAssign = updateChangeLog(issue, State.CREATED, State.OPEN, assigner, assignee, assignee, ChangeLogType.ASSIGN);
                changeLogs.add(changeLogAssign);
                issue.setChangeLogs(changeLogs);
            }
            issueRepository.save(issue);
            String message = assigner.getUsername().concat(Constants.CREATE_MESSAGE).concat(issue.getIssueId());
            createNotification(issue.getIssueId(), message);
            if (logger.isDebugEnabled())
                logger.debug("new Issue create successfully");
        } else {
            logger.error("project not found {}", issueRequest.getProjectId());
            throw new CustomException(CustomErrorCodes.PROJECT_NOT_FOUND);
        }
    }


    /**
     * Update Notification
     * @param issueId
     * @param message
     */
    private void createNotification(String issueId, String message) {
        Notification notification = new Notification();
        notification.setIssueId(issueId);
        notification.setMassage(message);
        notification.setIsSend(false);
        notificationRepository.save(notification);
    }

    /**
     * @param issue
     * @param fromState
     * @param toState
     * @param assigner
     * @param fromAssignee
     * @param toAssignee
     * @param changeLogType
     * @return
     */
    private ChangeLog updateChangeLog(Issue issue, State fromState, State toState,
                                      User assigner, User fromAssignee, User toAssignee, ChangeLogType changeLogType) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setAssigner(assigner);
        changeLog.setIssue(issue);
        changeLog.setFromAssignee(fromAssignee);
        changeLog.setToAssignee(toAssignee);
        changeLog.setFromState(fromState);
        changeLog.setToState(toState);
        changeLog.setStatus(Status.ACTIVE);
        changeLog.setType(changeLogType);
        return changeLog;
    }

    @Override
    public void updateIssue(IssueRequest issueRequest) {
        Optional<Project> project = projectRepository.findById(issueRequest.getProjectId());
        if (project.isPresent()) {
            if (logger.isDebugEnabled())
                logger.debug("Issue Update initiated");
            Optional<Issue> issue = issueRepository.findById(issueRequest.getId());
            if (issue.isPresent()) {
                issue.get().setTitle(issueRequest.getTitle());
                issue.get().setType(IssueType.getIssueType(issueRequest.getIssueType()));
                List<ChangeLog> changeLogList = new ArrayList<>();
                if (!issueRequest.getFromState().equals(issueRequest.getFromState())) {
                    if (logger.isDebugEnabled())
                        logger.debug("Change log initiated");
                    issue.get().setCurrentState(State.getState(issueRequest.getToState()));
                    ChangeLog changeLog = new ChangeLog();
                    changeLog.setIssue(issue.get());
                    changeLog.setFromState(State.getState(issueRequest.getFromState()));
                    changeLog.setToState(State.getState(issueRequest.getToState()));
                    changeLog.setStatus(Status.ACTIVE);
                    changeLogList.add(changeLog);
                    issue.get().setChangeLogs(changeLogList);
                }
                issueRepository.save(issue.get());
                if (logger.isDebugEnabled())
                    logger.debug("Issue Update successfully");
            } else {
                logger.error("project not found {}", issueRequest.getTitle());
                throw new CustomException(CustomErrorCodes.ISSUE_NOT_FOUND);
            }
        } else {
            logger.error("project not found {}", issueRequest.getProjectId());
            throw new CustomException(CustomErrorCodes.PROJECT_NOT_FOUND);
        }
    }

    @Override
    public BoardResponse getIssues(Long projectId, String state, String issueType, String assignee) {
        Optional<Project> project = projectRepository.findByIdAndStatus(projectId, Status.ACTIVE);
        if (project.isPresent()) {
            List<Issue> issues = new ArrayList<>();
            if (state.equals("0") && issueType.equals("0") && assignee.equals("0")) {
                issues = issueRepository.findAllByProjectIdAndStatus(project.get().getId(), Status.ACTIVE);
            } else if (!state.equals("0") && issueType.equals("0") && assignee.equals("0")) {
                issues = issueRepository.findAllByProjectIdAndCurrentStateAndStatus(project.get().getId(), State.getState(state), Status.ACTIVE);
            } else if (state.equals("0") && !issueType.equals("0") && assignee.equals("0")) {
                issues = issueRepository.findAllByProjectIdAndTypeAndStatus(project.get().getId(), IssueType.getIssueType(issueType), Status.ACTIVE);
            } else if (state.equals("0") && issueType.equals("0") && !assignee.equals("0")) {
                issues = issueRepository.findAllByProjectIdAndAssigneeIdAndStatus(project.get().getId(), Long.parseLong(assignee), Status.ACTIVE);
            } else if (!state.equals("0") && !issueType.equals("0") && assignee.equals("0")) {
                issues = issueRepository.findAllByProjectIdAndCurrentStateAndTypeAndStatus(project.get().getId(), State.getState(state), IssueType.getIssueType(issueType), Status.ACTIVE);
            } else if (state.equals("0") && !issueType.equals("0") && !assignee.equals("0")) {
                issues = issueRepository.findAllByProjectIdAndTypeAndAssigneeIdAndStatus(project.get().getId(), IssueType.getIssueType(issueType), Long.parseLong(assignee), Status.ACTIVE);
            } else if (!state.equals("0") && issueType.equals("0") && !assignee.equals("0")) {
                issues = issueRepository.findAllByProjectIdAndCurrentStateAndAssigneeIdAndStatus(project.get().getId(), State.getState(state), Long.parseLong(assignee), Status.ACTIVE);
            } else if (!state.equals("0") && !issueType.equals("0") && !assignee.equals("0")) {
                issues = issueRepository.findAllByProjectIdAndCurrentStateAndTypeAndAssigneeIdAndStatus(project.get().getId(), State.getState(state), IssueType.getIssueType(issueType), Long.parseLong(assignee), Status.ACTIVE);
            }

            List<IssueDto> issueOpen = new ArrayList<>();
            List<IssueDto> issueInProgress = new ArrayList<>();
            List<IssueDto> issueTesting = new ArrayList<>();
            List<IssueDto> issueDeploy = new ArrayList<>();

            for (Issue issue : issues) {
                switch (issue.getCurrentState()) {
                    case OPEN:
                        issueOpen.add(new IssueDto(issue));
                        break;
                    case IN_PROGRESS:
                        issueInProgress.add(new IssueDto(issue));
                        break;
                    case TESTING:
                        issueTesting.add(new IssueDto(issue));
                        break;
                    case DEPLOY:
                        issueDeploy.add(new IssueDto(issue));
                        break;
                }
            }
            List<ColumnsDto> columns = new ArrayList<>();
            columns.add(new ColumnsDto(State.OPEN.name(), State.OPEN.name(), issueOpen));
            columns.add(new ColumnsDto(State.IN_PROGRESS.name(), State.IN_PROGRESS.name(), issueInProgress));
            columns.add(new ColumnsDto(State.TESTING.name(), State.TESTING.name(), issueTesting));
            columns.add(new ColumnsDto(State.DEPLOY.name(), State.DEPLOY.name(), issueDeploy));
            BoardResponse boardResponse = new BoardResponse();
            boardResponse.setName(project.get().getName());
            boardResponse.setColumns(columns);
            return boardResponse;
        } else {
            throw new CustomException(CustomErrorCodes.PROJECT_NOT_FOUND);
        }
    }

    @Override
    public void changeState(IssueStateRequest issueStateRequest, Long userId) {
        Optional<Issue> issue = issueRepository.findByIdAndStatus(issueStateRequest.getIssueId(), Status.ACTIVE);
        if (issue.isPresent()) {
            issue.get().setCurrentState(State.getState(issueStateRequest.getToState()));
            User assigner = userRepository.findById(userId).orElseThrow(() ->
                    new CustomException(CustomErrorCodes.USER_NOT_FOUND));
            issue.get().getChangeLogs().add(updateChangeLog(issue.get(), State.getState(issueStateRequest.getFromState()),
                    State.getState(issueStateRequest.getToState()), assigner, issue.get().getAssignee(), issue.get().getAssignee(), ChangeLogType.STATE));
            issueRepository.save(issue.get());
            String message = assigner.getUsername().concat(" (" + issue.get().getIssueId() + ")").
                    concat(Constants.STATE_CHANGE).concat(issueStateRequest.getFromState()).
                    concat(Constants.TO).concat(issueStateRequest.getToState());
            createNotification(issue.get().getIssueId(), message);
        } else {
            throw new CustomException(CustomErrorCodes.ISSUE_NOT_FOUND);
        }
    }

    @Override
    public List<ChangeLogResponse> getChangeLogs(Long issueId) {
        Optional<Issue> issue = issueRepository.findByIdAndStatus(issueId, Status.ACTIVE);
        if (issue.isPresent()) {
            return changeLogRepository.findByIssueAndStatusOrderByCreatedDateAsc(issue.get(), Status.ACTIVE).stream()
                    .map(ChangeLogResponse::new).collect(Collectors.toList());
        } else {
            throw new CustomException(CustomErrorCodes.ISSUE_NOT_FOUND);
        }
    }

    @Override
    public IssueResponse getIssue(Long issueId) {
        Optional<Issue> issue = issueRepository.findByIdAndStatus(issueId, Status.ACTIVE);
        if (issue.isPresent()) {
            return new IssueResponse(issue.get());
        } else {
            throw new CustomException(CustomErrorCodes.ISSUE_NOT_FOUND);
        }
    }

    @Override
    public void assignIssue(AssignRequest assignRequest, Long userId) {
        Optional<Issue> issue = issueRepository.findByIdAndStatus(assignRequest.getIssueId(), Status.ACTIVE);
        if (issue.isPresent()) {
            User fromAssignee = issue.get().getAssignee();
            User toAssignee = userRepository.findById(assignRequest.getToAssignee()).orElseThrow(() ->
                    new CustomException(CustomErrorCodes.USER_NOT_FOUND));
            User assigner = userRepository.findById(userId).orElseThrow(() ->
                    new CustomException(CustomErrorCodes.USER_NOT_FOUND));
            issue.get().setAssignee(toAssignee);
            issue.get().getChangeLogs().add(updateChangeLog(issue.get(), issue.get().getCurrentState(),
                    issue.get().getCurrentState(), assigner, fromAssignee, toAssignee, ChangeLogType.ASSIGN));
            issueRepository.save(issue.get());
            String message = assigner.getUsername().concat(Constants.ASSIGN_MESSAGE).
                    concat(issue.get().getIssueId()).concat(Constants.TO).concat(toAssignee.getUsername());
            createNotification(issue.get().getIssueId(), message);
        } else {
            throw new CustomException(CustomErrorCodes.ISSUE_NOT_FOUND);
        }
    }
}
