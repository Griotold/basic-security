package io.security.basicsecurity.security.metadatasource;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap = new LinkedHashMap<>();

    public UrlFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourcesMap) {
        this.requestMap = resourcesMap;
    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 사용자의 요청을 가져온다. FilterInvocation
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        // 권한 정보를 추출하는 로직
        if (requestMap != null) {
            for(Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
                // DB에서 가져온 것들 중에 key값만 추출
                RequestMatcher matcher = entry.getKey();
                // DB에서 가져온 key값과 사용자가 요청한 url 자원이 일치하면 권한 정보를 return
                if(matcher.matches(request)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
    // DefaultFilterInvocationSecurityMetadataSource 에서 로직 가져옴
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        this.requestMap.values().forEach(allAttributes::addAll);
        return allAttributes;
    }
    // DefaultFilterInvocationSecurityMetadataSource 에서 로직 가져옴
    // 타입 검사
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
