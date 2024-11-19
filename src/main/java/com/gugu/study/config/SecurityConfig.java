package com.gugu.study.config;

import com.gugu.study.auth.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
//    private final JwtExceptionFilter jwtExceptionFilter;
//    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/login", "/api/register", "/api/token/reissue", "/api/logout").permitAll()
                        .anyRequest().authenticated()  // 그 외 요청은 인증 필요
                )
//                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint))
                  .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore(jwtExceptionFilter, JwtFilter.class);

        return http.build();
    }

    @Bean // 이 메소드의 리턴값을 Bean으로 지정하겠다
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
