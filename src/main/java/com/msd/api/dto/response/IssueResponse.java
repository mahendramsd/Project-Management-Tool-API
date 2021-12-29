package com.msd.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msd.api.domain.Issue;
import com.msd.api.util.ChangeLogType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class IssueResponse implements Serializable {

    private Long id;
    private String title;
    private String issueId;
    private String currentState;
    private String assigner;
    private String assignee;
    private Long assigneeId;
    private LocalDateTime createDate;

    public IssueResponse(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.issueId = issue.getIssueId();
        this.currentState = issue.getCurrentState().name();
        this.assigner = issue.getChangeLogs().stream().filter(changeLog ->
                changeLog.getType().equals(ChangeLogType.CREATE)).findFirst().get().getAssigner().getUsername();
        this.assignee = issue.getAssignee().getUsername();
        this.assigneeId = issue.getAssignee().getId();
        this.createDate = issue.getCreatedDate();
    }
}
