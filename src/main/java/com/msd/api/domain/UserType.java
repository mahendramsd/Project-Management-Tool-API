package com.msd.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "user_type")
public class UserType implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "userType")
    @JsonIgnore
    private List<User> users;
}
