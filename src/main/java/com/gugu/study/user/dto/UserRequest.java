package com.gugu.study.user.dto;

import com.gugu.study.user.entity.User;
import lombok.*;

@Getter
@ToString
public class UserRequest {
    private Long id;
    private String username;//gugu99
    private String password; //fgyubh ->e46dfgugy7dr

    public void setPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
    public User toEntity() {
        return new User(username, password);
    }
}
