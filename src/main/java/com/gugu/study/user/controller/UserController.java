package com.gugu.study.user.controller;

import com.gugu.study.user.dto.UserRequest;
import com.gugu.study.user.dto.UserResponse;
import com.gugu.study.user.entity.User;
import com.gugu.study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/api/register")
    public Long resgister(@RequestBody UserRequest request) {
        return userService.save(request);
    }

    //로그인
    @PostMapping("/api/login")
    public String login (@RequestBody UserRequest request) {
        return userService.login(request);
    }

    // password 변경
    @PutMapping("/api/userPassword")
    public void updatePassword (@AuthenticationPrincipal UserDetails userDetails, @RequestBody String password) {
        Long id = Long.parseLong(userDetails.getUsername());
        userService.updatePassword(password, id);
    }

    // 회원정보 수정
    @PutMapping("/api/user")
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody String name) {
        Long id = Long.parseLong(userDetails.getUsername());

        return  ResponseEntity.ok(userService.updateUser(name, id));
    }

    //액세스 토큰 확인 테스트 컨트롤러
    @PostMapping("/api/test")
    public String test(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("user: {}", userDetails);
        return "Success";
    }
}
