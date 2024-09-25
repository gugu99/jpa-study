package com.gugu.study.user.dto;

import com.gugu.study.user.entity.User;
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
