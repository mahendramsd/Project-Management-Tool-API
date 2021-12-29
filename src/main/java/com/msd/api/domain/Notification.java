package com.msd.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "notification")
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "massage")
    private String massage;

    @JoinColumn(name = "issue_id")
    private String issueId;

    @Column(name = "is_send")
    private Boolean isSend;



}
