package com.gugu.study.user.controller;

import com.gugu.study.user.dto.UserRequest;
import com.gugu.study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
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

    // Username 변경
    @PostMapping("/api/userModify")
    public void userModify (@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserRequest request) {
        userService.userModify(request);
    }

    //액세스 토큰 확인 테스트 컨트롤러
    @PostMapping("/api/test")
    public String test(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("user: {}", userDetails);
        return "Success";
    }
}
