package io.security.basicsecurity.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.security.basicsecurity.domain.Account;
import io.security.basicsecurity.domain.AccountDto;
import io.security.basicsecurity.security.token.AjaxAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        // 필터 동작 조건 : 아래 URL로 요청시 && Ajax 요청시
        super(new AntPathRequestMatcher("/api/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // Ajax 요청시 인증 처리를 수행하고
        // 아니면 예외를 던져라
        if(!isAjax(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }

        // request에서 전달한 JSON을 객체(AccountDto)로 변환
        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);
        // username이 비었거나 password가 비었으면 예외를 던진다
        if(!StringUtils.hasText(accountDto.getUsername()) || !StringUtils.hasText(accountDto.getPassword())) {
            throw new IllegalArgumentException("Username or Password is empty");
        }
        // 인증 전 토큰 생성
        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(accountDto.getUsername(), accountDto.getPassword());

        // AuthenticationManager에게 인증 위임
        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }
    // request 헤더에 있는 정보로 Ajax 방식인지 아닌지 판단
    private boolean isAjax(HttpServletRequest request) {

        if ("XMLHttpRequest".equals(request.getHeader("X-Request-With"))) {
            return true;
        }
        return false;
    }
}
