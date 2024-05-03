package com.prj1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class AppConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.formLogin(login -> login.loginPage("/member/login"));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/*
1. 로그인한 사람만 글쓰기 가능
2. 글작성시 작성자는 로그인한 사람의 이메일
3. 자기글만 수정/삭제

1. 회원목록은 관리자만 접근 가능
2. 회원정보는 본인과 관리자만 볼 수 있게
3. 회원정보는 본인만
*/