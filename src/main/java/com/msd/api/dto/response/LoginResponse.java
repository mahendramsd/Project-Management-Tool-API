package com.msd.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msd.api.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class LoginResponse implements Serializable {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("access_token")
    private String accessToken;

    public LoginResponse(User user) {
        this.userId = user.getId();
        this.accessToken = user.getAccessToken();
        this.username = user.getUsername();
    }
}
