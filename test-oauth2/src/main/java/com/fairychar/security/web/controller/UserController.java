package com.fairychar.security.web.controller;

import com.fairychar.security.beans.GxOAuth2RestTemplate;
import com.fairychar.security.beans.SimpleOAuth2RestTemplate;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Source;
import java.util.Map;

/**
 * Datetime: 2021/3/25 14:35 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@RestController
@RequestMapping("/")
@Api(tags = "用户信息")
public class UserController {
    @Autowired
    private GxOAuth2RestTemplate restTemplate;
    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;
    @Autowired
    private SimpleOAuth2RestTemplate simpleOAuth2RestTemplate;
    @GetMapping("/me")
    @ApiOperation("我的信息")
    public Object me() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    @GetMapping("/hi")
    @ApiOperation("hi")
    public Object hi() {
        return "hi";
    }

    @GetMapping("gxo")
    public Object gxo(){
        this.restTemplate.getAccessToken();
        return "o";
    }

    @GetMapping("o")
    public Object o(){
        System.out.println(this.oAuth2RestTemplate);
        ResponseEntity<Object> exchange = this.oAuth2RestTemplate.exchange("https://checking.cdiisp.com:8800/uias/sso/getAccessToken", HttpMethod.POST
                , null, Object.class, Maps.newHashMap());
        this.oAuth2RestTemplate.getAccessToken();
        return this.oAuth2RestTemplate.getOAuth2ClientContext();
    }


    @GetMapping("o2")
    public Object o2(){
        System.out.println(simpleOAuth2RestTemplate);
        ResponseEntity<Object> exchange = this.simpleOAuth2RestTemplate.exchange("https://checking.cdiisp.com:8800/uias/sso/getAccessToken", HttpMethod.POST
                , null, Object.class, Maps.newHashMap());
        this.oAuth2RestTemplate.getAccessToken();
        return this.oAuth2RestTemplate.getOAuth2ClientContext();
    }

    @GetMapping("ent")
    public Object ent(){
        ResponseEntity<Object> objectResponseEntity = this.oAuth2RestTemplate.postForEntity("https://checking.cdiisp.com:8800/uias/ssoOrgUser/getEntInfo"
                , new HttpEntity<>(new PageRequest(100, 1)), Object.class, Maps.newHashMap());
        System.out.println(objectResponseEntity.getBody());
        return objectResponseEntity;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class PageRequest{
        private int pageSize;
        private int pageNum;
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