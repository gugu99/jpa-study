package com.gugu.study.auth.controller;

import com.gugu.study.auth.UserPrincipal;
import com.gugu.study.auth.dto.*;
import com.gugu.study.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;

    //회원가입
    @PostMapping("/api/register")
    public Long resgister(@RequestBody UserRequest request) {
        return authService.save(request);
    }

    //로그인
    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login (@RequestBody UserRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    // 새로운 액세스 토큽 발급
    @PostMapping("/api/token")
    public ResponseEntity<RefreshTokenResponse> createNewAccessToken(@RequestBody RefreshTokenRequest request){
        String token = authService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new RefreshTokenResponse(token));
    }

    // password 변경
    @PutMapping("/api/userPassword")
    public void updatePassword (@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody String password) {
        authService.updatePassword(password, userPrincipal.getId());
    }

    // 회원정보 수정
    @PutMapping("/api/user")
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody String name) {

        return  ResponseEntity.ok(authService.updateUser(name, userPrincipal.getId()));
    }

    //액세스 토큰 확인 테스트 컨트롤러
    @PostMapping("/api/test")
    public String test(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("user: {}", userPrincipal);
        return "Success";
    }
}
