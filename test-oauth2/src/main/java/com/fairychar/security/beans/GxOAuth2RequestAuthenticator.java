package com.fairychar.security.beans;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RequestAuthenticator;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Datetime: 2021/3/29 14:52 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
public class GxOAuth2RequestAuthenticator implements OAuth2RequestAuthenticator {

    @Override
    public void authenticate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext clientContext,
                             ClientHttpRequest request) {
        OAuth2AccessToken accessToken = clientContext.getAccessToken();
        if (accessToken == null) {
            throw new AccessTokenRequiredException(resource);
        }
        String tokenType = accessToken.getTokenType();
        if (!StringUtils.hasText(tokenType)) {
            tokenType = OAuth2AccessToken.BEARER_TYPE; // we'll assume basic bearer token type if none is specified.
        } else if (tokenType.equalsIgnoreCase(OAuth2AccessToken.BEARER_TYPE)) {
            // gh-1346
            tokenType = OAuth2AccessToken.BEARER_TYPE; // Ensure we use the correct syntax for the "Bearer" authentication scheme
        }
        HttpHeaders httpHeaders = request.getHeaders();
        httpHeaders.set("Authorization", String.format("%s %s", tokenType, accessToken.getValue()));
        httpHeaders.set("client_id",resource.getClientId());
        httpHeaders.set("accessToken", accessToken.getValue());
        httpHeaders.set("timeStamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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