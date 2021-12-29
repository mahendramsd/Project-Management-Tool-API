package com.msd.api.domain;

import com.msd.api.util.ChangeLogType;
import com.msd.api.util.State;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "change_log")
public class ChangeLog extends BaseEntityMaster{

    @Column(name = "from_state")
    @Enumerated(EnumType.STRING)
    private State fromState;

    @Column(name = "to_state")
    @Enumerated(EnumType.STRING)
    private State toState;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    // Issue Assigner
    @ManyToOne
    @JoinColumn(name = "assigner_id")
    private User assigner;

    // Assign person
    @ManyToOne
    @JoinColumn(name = "from_assignee_id")
    private User fromAssignee;

    @ManyToOne
    @JoinColumn(name = "to_assignee_id")
    private User toAssignee;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ChangeLogType type;

}
