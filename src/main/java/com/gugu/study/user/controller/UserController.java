package com.gugu.study.user.controller;

import com.gugu.study.user.auth.UserPrincipal;
import com.gugu.study.user.dto.UserRequest;
import com.gugu.study.user.dto.UserResponse;
import com.gugu.study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public void updatePassword (@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody String password) {
        userService.updatePassword(password, userPrincipal.getId());
    }

    // 회원정보 수정
    @PutMapping("/api/user")
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody String name) {

        return  ResponseEntity.ok(userService.updateUser(name, userPrincipal.getId()));
    }

    //액세스 토큰 확인 테스트 컨트롤러
    @PostMapping("/api/test")
    public String test(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("user: {}", userPrincipal);
        return "Success";
    }
}
