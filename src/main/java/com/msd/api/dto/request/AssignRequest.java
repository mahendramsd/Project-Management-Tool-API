package com.msd.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class AssignRequest implements Serializable {

    private Long issueId;
    private Long toAssignee;
}
