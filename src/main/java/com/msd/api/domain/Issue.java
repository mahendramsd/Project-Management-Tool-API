package com.msd.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msd.api.util.State;
import com.msd.api.util.IssueType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "issue")
public class Issue extends BaseEntityMaster{

    @Column(name = "issue_id")
    private String issueId;

    @Column(name = "title")
    private String title;


    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private IssueType type;

    @Column(name = "current_state")
    @Enumerated(EnumType.STRING)
    private State currentState;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    // Assign person
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "issue")
    @JsonIgnore
    private List<ChangeLog> changeLogs;

}
