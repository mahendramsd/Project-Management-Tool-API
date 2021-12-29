package com.msd.api.dto.response;

import com.msd.api.domain.Issue;
import com.msd.api.dto.IssueDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
@EqualsAndHashCode
public class IssueSprintResponse implements Serializable {

    private HashMap<String,List<IssueDto>> issueList;
}
