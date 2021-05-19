package com.shininghyunho.board.config.auth;

import com.shininghyunho.board.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/profile").permitAll()
                    .antMatchers("/api/v1/posts/**").hasRole(Role.USER.name())
                    // 유저 관리는 어드민만
                    .antMatchers("/api/v1/user/**").hasRole(Role.ADMIN.name())
                    // 게시글 뷰에 접근은 누구나 가능 (api 호출은 권한이 있어야함)
                    .antMatchers("/posts/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()// OAuth2 로그인 이후 사용자 정보를 가져옴
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
