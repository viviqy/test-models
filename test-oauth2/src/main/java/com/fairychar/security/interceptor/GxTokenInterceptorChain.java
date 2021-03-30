package com.fairychar.security.interceptor;

import com.fairychar.security.pojo.dto.RemoteUserDTO;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Datetime: 2021/3/25 11:27 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@Component
public class GxTokenInterceptorChain extends OncePerRequestFilter {
    @Autowired
    private OAuth2RestTemplate restTemplate;
    @Value("${security.oauth2.resource.token-info-uri}")
    private String validateUrl;
    private AntPathMatcher matcher = new AntPathMatcher();
    private String pattern = "/public/**";
    private String tokenName = "gx-token";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (matcher.match(pattern, httpServletRequest.getRequestURI())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        String token = httpServletRequest.getHeader(tokenName);
        if (Strings.isNullOrEmpty(token)) {
            return;
        }
        ResponseEntity<RemoteUserDTO> validateEntity = restTemplate.postForEntity(validateUrl, new HttpEntity<>(new HttpHeaders() {{
            set("token", token);
        }}), RemoteUserDTO.class, Maps.newHashMap());
        if (validate(validateEntity)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private boolean validate(ResponseEntity<RemoteUserDTO> validateEntity) {
        return false;
    }

}
/*
                                      /[-])//  ___        
                                 __ --\ `_/~--|  / \      
                               /_-/~~--~~ /~~~\\_\ /\     
                               |  |___|===|_-- | \ \ \    
____________ _/~~~~~~~~|~~\,   ---|---\___/----|  \/\-\   
____________ ~\________|__/   / // \__ |  ||  / | |   | | 
                      ,~-|~~~~~\--, | \|--|/~|||  |   | | 
                      [3-|____---~~ _--'==;/ _,   |   |_| 
                                  /   /\__|_/  \  \__/--/ 
                                 /---/_\  -___/ |  /,--|  
                                 /  /\/~--|   | |  \///   
                                /  / |-__ \    |/         
                               |--/ /      |-- | \        
                              \^~~\\/\      \   \/- _     
                               \    |  \     |~~\~~| \    
                                \    \  \     \   \  | \  
                                  \    \ |     \   \    \ 
                                   |~~|\/\|     \   \   | 
                                  |   |/         \_--_- |\
                                  |  /            /   |/\/
                                   ~~             /  /    
                                                 |__/   W<

*/