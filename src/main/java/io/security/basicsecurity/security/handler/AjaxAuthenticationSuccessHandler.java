package io.security.basicsecurity.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.security.basicsecurity.domain.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    // 이번에는 account 엔티티 객체를 JSON으로 변환
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 인증 성공 후의 상황 : principal에 Account 객체가 들어 있다.
        // Object principal = authentication.getPrincipal();
        Account account = (Account) authentication.getPrincipal();
        
        // response 에 정보 담기
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        // account 객체를 JSON으로 변환해서 클라이언트에 전달
        objectMapper.writeValue(response.getWriter(), account);
    }
}
