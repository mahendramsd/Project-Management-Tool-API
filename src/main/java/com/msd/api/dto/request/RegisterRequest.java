package com.msd.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class RegisterRequest implements Serializable {

    private String username;
    private Long userType;
    private String email;
    private String password;
}
