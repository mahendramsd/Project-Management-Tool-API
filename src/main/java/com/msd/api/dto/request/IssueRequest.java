package com.msd.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class IssueRequest implements Serializable {

    private Long id;
    private String title;
    private Long projectId;
    private String issueType;
    private String fromState;
    private String toState;
    private Long userId;
}
