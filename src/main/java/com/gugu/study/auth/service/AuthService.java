package com.gugu.study.auth.service;

import com.gugu.study.auth.TokenProvider;
import com.gugu.study.auth.entity.RefreshToken;
import com.gugu.study.auth.repository.RefreshTokenRepository;
import com.gugu.study.auth.dto.LoginResponse;
import com.gugu.study.auth.dto.UserRequest;
import com.gugu.study.auth.dto.UserResponse;
import com.gugu.study.auth.entity.User;
import com.gugu.study.exception.DuplicateUsernameException;
import com.gugu.study.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원가입
    public Long save(UserRequest dto) {

        Optional<User> user = userRepository.findByUsername(dto.getUsername());

        if(user.isPresent()){
            throw new DuplicateUsernameException(dto.getUsername() + "은/는 이미 사용중인 아이디입니다.");
        }

        dto.setPassword(encoder.encode(dto.getPassword()));

        return userRepository.save(dto.toEntity()).getId();
    }

    // 로그인
    public LoginResponse login(UserRequest dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지않는 아이디입니다."));

        boolean isPasswordMatch = encoder.matches(dto.getPassword(), user.getPassword());

        if(!isPasswordMatch) {
            throw new BadCredentialsException("비밀번호가 일치하지않습니다.");
        }

        String accessToken = tokenProvider.generateToken(user, Duration.ofMinutes(15));
        String refreshToken = UUID.randomUUID().toString();

        // refresh token 저장
        refreshTokenRepository.save(new RefreshToken(user, refreshToken));

        return new LoginResponse(accessToken, refreshToken);
    }

    // 새로운 액세스 토큰 발급
    public String createNewAccessToken(String refreshToken) {
        RefreshToken savedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("유효하지않은 refreshToken입니다."));

        // 토큰 유효성 검사
        if(savedRefreshToken.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("refreshToken이 만료되었습니다.");
        }

        return tokenProvider.generateToken(savedRefreshToken.getUser(), Duration.ofMinutes(15));
    }

    //  password 변경
    @Transactional
    public void updatePassword(String password, Long id) {
        // 회원정보가 있는지 확인
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("잘못된 회원정보입니다."));

        user.setPassword(encoder.encode(password));
    }

    // 회원정보 수정
    @Transactional
    public UserResponse updateUser(String name, Long id) {
        // 회원정보가 있는지 확인
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원정보가 존재하지 않습니다."));

        user.setName(name);

        return new UserResponse(user);
    }
}
