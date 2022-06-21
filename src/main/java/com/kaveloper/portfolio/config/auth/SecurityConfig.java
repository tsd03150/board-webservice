package com.kaveloper.portfolio.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // url별 권한 관리 설정
                .authorizeRequests()
                // permitAll()은 전체 사용자가 이용 가능
                .antMatchers("/", "/starter-template.css", "/css/**", "/images/**", "/js/**", "/profile", "/board/list/**", "/board/detail/**").permitAll()
                // 나머지 url은 인증된 사용자만 사용가능
                .anyRequest().authenticated()

                // 로그 아웃 설정
                .and()
                .logout()
                .logoutSuccessUrl("/")

                // 로그인 성공 후 customOAuth2UserService UserService 구현체로 이동
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

    }
}
