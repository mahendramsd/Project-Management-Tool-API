package com.msd.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "project")
public class Project extends BaseEntityMaster{

    @Column(name = "project_id",unique = true)
    private String projectId;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "project")
    @JsonIgnore
    private List<Issue> issues;

}
