package com.msd.api.service;

import com.msd.api.dto.request.AssignRequest;
import com.msd.api.dto.request.IssueRequest;
import com.msd.api.dto.request.IssueStateRequest;
import com.msd.api.dto.response.BoardResponse;
import com.msd.api.dto.response.ChangeLogResponse;
import com.msd.api.dto.response.IssueResponse;
import com.msd.api.dto.response.IssueSprintResponse;

import java.util.List;

public interface IssueService {

    void createIssue(IssueRequest issueRequest,Long userId);

    void updateIssue(IssueRequest issueRequest);

    BoardResponse getIssues(Long projectId,String state,String issueType, String assignee);

    void changeState(IssueStateRequest issueStateRequest,Long userId);

    List<ChangeLogResponse> getChangeLogs(Long issueId);

    IssueResponse getIssue(Long issueId);

    void assignIssue(AssignRequest assignRequest, Long userId);
}
