package com.msd.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msd.api.domain.ChangeLog;
import com.msd.api.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class ChangeLogResponse implements Serializable {

    private LocalDateTime changeOn;
    private String fromState;
    private String toState;
    private String type;
    private String assigner;
    private String fromAssignee;
    private String toAssignee;

    public ChangeLogResponse(ChangeLog changeLog) {
        this.changeOn = changeLog.getCreatedDate();
        this.fromState = changeLog.getFromState().name();
        this.toState = changeLog.getToState().name();
        this.type = changeLog.getType().name();
        this.assigner = changeLog.getAssigner().getUsername();
        this.fromAssignee = changeLog.getFromAssignee().getUsername();
        this.toAssignee = changeLog.getToAssignee().getUsername();
    }
}
