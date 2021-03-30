package com.fairychar.security.configuration;

import com.fairychar.security.beans.GxClientAuthenticationHandler;
import com.fairychar.security.beans.GxOAuth2RequestAuthenticator;
import com.fairychar.security.beans.GxOAuth2RestTemplate;
import com.fairychar.security.beans.SimpleOAuth2RestTemplate;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Datetime: 2021/3/22 14:02 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@Configuration
public class BeansConfiguration {
    private Gson gson = new Gson();

    @Bean
    SimpleOAuth2RestTemplate simpleOAuth2RestTemplate(OAuth2ProtectedResourceDetails resourceDetails) {
        return new SimpleOAuth2RestTemplate(resourceDetails);
    }

    @Bean
    RestTemplate restTemplate() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setClientId("gxdj");
        resourceDetails.setClientSecret("9VvJvgaImYgC61Eyx3g0fFtMn0Yu1FdijU43BgPF5ZP8DhYpUmM9Fhye9NdV2C8t5nFz8nsOznonK+DDPDFgBg==\"");
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails);
        return oAuth2RestTemplate;
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails) {
        ClientCredentialsAccessTokenProvider clientCredentialsAccessTokenProvider = new ClientCredentialsAccessTokenProvider();
        clientCredentialsAccessTokenProvider.setAuthenticationHandler(new GxClientAuthenticationHandler());
        new AccessTokenProviderChain(Arrays.<AccessTokenProvider>asList(
                new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
                new ResourceOwnerPasswordAccessTokenProvider(), clientCredentialsAccessTokenProvider));
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(oAuth2ProtectedResourceDetails) {
            @Override
            protected OAuth2AccessToken acquireAccessToken(OAuth2ClientContext oauth2Context) throws UserRedirectRequiredException {
                AccessTokenRequest accessTokenRequest = oauth2Context.getAccessTokenRequest();
                if (accessTokenRequest == null) {
                    throw new AccessTokenRequiredException(
                            "No OAuth 2 security context has been established. Unable to access resource '"
                                    + oAuth2ProtectedResourceDetails.getId() + "'.", oAuth2ProtectedResourceDetails);
                }

                // Transfer the preserved state from the (longer lived) context to the current request.
                String stateKey = accessTokenRequest.getStateKey();
                if (stateKey != null) {
                    accessTokenRequest.setPreservedState(oauth2Context.removePreservedState(stateKey));
                }

                OAuth2AccessToken existingToken = oauth2Context.getAccessToken();
                if (existingToken != null) {
                    accessTokenRequest.setExistingToken(existingToken);
                }

                OAuth2AccessToken accessToken = null;
                OAuth2AccessToken unHandleMsg = clientCredentialsAccessTokenProvider.obtainAccessToken(oAuth2ProtectedResourceDetails, accessTokenRequest);
                try {
                    Map dataMap = (Map) unHandleMsg.getAdditionalInformation().get("data");
                    String token = (String) dataMap.get("accessToken");
                    String expire = (String) dataMap.get("deadline");//yyyy-MM-dd HH:mm:ss
                    Date expireDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expire);
                    DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(token);
                    oAuth2AccessToken.setScope(Sets.newHashSet("all"));
                    oAuth2AccessToken.setExpiration(expireDate);
                    accessToken = oAuth2AccessToken;

                } catch (Exception ignore) {
                }
                if (accessToken == null || accessToken.getValue() == null) {
                    throw new IllegalStateException(
                            "Access token provider returned a null access token, which is illegal according to the contract.");
                }
                oauth2Context.setAccessToken(accessToken);
                return accessToken;
            }
        };
        oAuth2RestTemplate.setAccessTokenProvider(clientCredentialsAccessTokenProvider);
        oAuth2RestTemplate.setAuthenticator(new GxOAuth2RequestAuthenticator());
        return oAuth2RestTemplate;
    }


    @Bean
    GxOAuth2RestTemplate gxOAuth2RestTemplate() {
        return new GxOAuth2RestTemplate();
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