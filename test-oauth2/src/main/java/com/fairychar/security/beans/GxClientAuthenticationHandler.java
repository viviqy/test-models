package com.fairychar.security.beans;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.auth.ClientAuthenticationHandler;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Datetime: 2021/3/26 10:43 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
public class GxClientAuthenticationHandler implements ClientAuthenticationHandler {
    @Override
    public void authenticateTokenRequest(OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form,
                                         HttpHeaders headers) {
        if (resource.isAuthenticationRequired()) {
            headers.set("client_id", "gxdj");
            headers.set("authenticator", "9VvJvgaImYgC61Eyx3g0fFtMn0Yu1FdijU43BgPF5ZP8DhYpUmM9Fhye9NdV2C8t5nFz8nsOznonK+DDPDFgBg==");
            AuthenticationScheme scheme = AuthenticationScheme.header;
            if (resource.getClientAuthenticationScheme() != null) {
                scheme = resource.getClientAuthenticationScheme();
            }

            try {
                String clientSecret = resource.getClientSecret();
                clientSecret = clientSecret == null ? "" : clientSecret;
                switch (scheme) {
                    case header:
                        form.remove("client_secret");
                        headers.add(
                                "Authorization",
                                String.format(
                                        "Basic %s",
                                        new String(Base64.encode(String.format("%s:%s", resource.getClientId(),
                                                clientSecret).getBytes("UTF-8")), "UTF-8")));
                        break;
                    case form:
                    case query:
                        form.set("client_id", resource.getClientId());
                        if (StringUtils.hasText(clientSecret)) {
                            form.set("authenticator", clientSecret);
                        }
                        break;
                    default:
                        throw new IllegalStateException(
                                "Default authentication handler doesn't know how to handle scheme: " + scheme);
                }
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
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