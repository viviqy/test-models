package com.fairychar.security.beans;

import com.fairychar.security.pojo.dto.TokenDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Datetime: 2021/3/25 16:37 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */

@Component
public class GxOAuth2RestTemplate {

    @Autowired
    private RestTemplate restTemplate;
    private TokenDTO token;
    private long lastOAuthTime = System.currentTimeMillis();

    public void getAccessToken() throws UserRedirectRequiredException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("client_id", "gxdj");
        httpHeaders.set("authenticator", "9VvJvgaImYgC61Eyx3g0fFtMn0Yu1FdijU43BgPF5ZP8DhYpUmM9Fhye9NdV2C8t5nFz8nsOznonK+DDPDFgBg==");
        ResponseEntity<GxResponse> entity = this.restTemplate.exchange("https://checking.cdiisp.com:8800/uias/sso/getAccessToken", HttpMethod.POST, new HttpEntity<>(httpHeaders), GxResponse.class);
        DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken("");
        if (HttpStatus.OK.equals(entity.getStatusCode())) {
            String accessToken = entity.getBody().getData().getAccessToken();
            oAuth2AccessToken.setValue(accessToken);
            this.token = entity.getBody().getData();
        }
    }


    @NoArgsConstructor
    @Data
    public static class GxResponse {

        /**
         * appId : gxdj
         * timeStamp : 2021-03-25 14:49:17
         * result : 1
         * errorDescription : null
         * data : {"accessToken":"c384bc4e6018ad1f097856ec3046948b","deadline":"2021-03-25 16:49:17"}
         */

        @JsonProperty("appId")
        private String appId;
        @JsonProperty("timeStamp")
        private String timeStamp;
        @JsonProperty("result")
        private Integer result;
        @JsonProperty("errorDescription")
        private Object errorDescription;
        @JsonProperty("data")
        private TokenDTO data;

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