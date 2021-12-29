package com.msd.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msd.api.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class UserResponse implements Serializable {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("userType")
    private String userType;

    public UserResponse(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.userType = user.getUserType().getName();
        this.email = user.getEmail();
    }
}
