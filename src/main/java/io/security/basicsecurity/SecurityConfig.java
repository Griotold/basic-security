package io.security.basicsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 요청에 대한 보안 검사
                .anyRequest().authenticated(); // 어떤 요청에도 인증을 받음
        // 인증 정책 : 폼 로그인 방식만 허용
        http
                .formLogin();

        // 세션 고정 보호 : 세션 고정 공격 방어 기능
        http
                .sessionManagement()
                // .sessionFixation().none(); -> 세션 고정 공격에 속수무책으로 당한다.
                .sessionFixation().changeSessionId(); // 인증 할 때마다 세션 ID가 바뀐다.
    }
}
