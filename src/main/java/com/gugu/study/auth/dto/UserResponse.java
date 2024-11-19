package com.gugu.study.auth.dto;

import com.gugu.study.auth.entity.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserResponse {
    private String username;
    private String name;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
    }

}
