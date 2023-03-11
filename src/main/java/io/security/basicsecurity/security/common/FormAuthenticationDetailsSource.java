package io.security.basicsecurity.security.common;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
@Component // 설정 클래스에 넣어줘야 하므로 빈으로 만든다.
public class FormAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    // WebAuthenticationDetails를 생성하는 buildDetails() Override
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new FormWebAuthenticationDetails(context);
    }
}
