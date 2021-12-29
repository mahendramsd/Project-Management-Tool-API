package com.msd.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntityMaster {

    @Column(name = "user_name",unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "access_token")
    private String accessToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type_id")
    @JsonIgnore
    private UserType userType;

    @OneToMany(mappedBy = "assignee")
    @JsonIgnore
    private List<Issue> issues;
}
