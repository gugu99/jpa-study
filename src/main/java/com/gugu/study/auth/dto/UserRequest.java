package com.gugu.study.auth.dto;

import com.gugu.study.auth.entity.User;
import lombok.*;

@Getter
@ToString
public class UserRequest {
    private String username;//gugu99
    private String name; //닉네임
    private String password; //fgyubh ->e46dfgugy7dr

    public void setPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
    public User toEntity() {
        return new User(username, name, password);
    }
}
