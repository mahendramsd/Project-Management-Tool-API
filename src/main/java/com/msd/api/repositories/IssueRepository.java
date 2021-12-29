package com.msd.api.repositories;

import com.msd.api.domain.Issue;
import com.msd.api.domain.Project;
import com.msd.api.util.IssueType;
import com.msd.api.util.State;
import com.msd.api.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {

    List<Issue> findAllByProjectIdAndStatus(Long id, Status active);

    Optional<Issue> findByIdAndStatus(Long id, Status active);

    List<Issue> findAllByProjectIdAndCurrentStateAndStatus(Long id, State state, Status active);

    List<Issue> findAllByProjectIdAndTypeAndStatus(Long id, IssueType issueType, Status active);

    List<Issue> findAllByProjectIdAndAssigneeIdAndStatus(Long id, Long assignee, Status active);

    List<Issue> findAllByProjectIdAndCurrentStateAndTypeAndStatus(Long id, State state, IssueType issueType, Status active);

    List<Issue> findAllByProjectIdAndTypeAndAssigneeIdAndStatus(Long id, IssueType issueType, long parseLong, Status active);

    List<Issue> findAllByProjectIdAndCurrentStateAndAssigneeIdAndStatus(Long id, State state, long parseLong, Status active);

    List<Issue> findAllByProjectIdAndCurrentStateAndTypeAndAssigneeIdAndStatus(Long id, State state, IssueType issueType, long parseLong, Status active);
}
