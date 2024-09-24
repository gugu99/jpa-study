package com.gugu.study.user.service;

import com.gugu.study.user.auth.TokenProvider;
import com.gugu.study.user.dto.UserRequest;
import com.gugu.study.user.entity.User;
import com.gugu.study.exception.DuplicateUsernameException;
import com.gugu.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenProvider tokenProvider;
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
    public String login(UserRequest dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지않는 아이디입니다."));

        boolean isPasswordMatch = encoder.matches(dto.getPassword(), user.getPassword());

        if(!isPasswordMatch) {
            throw new BadCredentialsException("비밀번호가 일치하지않습니다.");
        }

        return tokenProvider.generateToken(user, Duration.ofHours(3));
    }

    // 회원 정보 수정
    public void userModify(UserRequest dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("잘못된 회원정보입니다."));

    }
}
