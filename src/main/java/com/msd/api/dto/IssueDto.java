package com.msd.api.dto;

import com.msd.api.domain.Issue;
import lombok.Data;

import java.io.Serializable;

@Data
public class IssueDto implements Serializable {

    private Long id;
    private String title;
    private String issueId;
    private String type;
    private String assignee;

    public IssueDto(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.issueId = issue.getIssueId();
        this.type = issue.getType().name();
        this.assignee = issue.getAssignee().getUsername();
    }
}
