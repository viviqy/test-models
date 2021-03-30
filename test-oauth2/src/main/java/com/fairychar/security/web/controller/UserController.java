package com.fairychar.security.web.controller;

import com.fairychar.security.beans.GxOAuth2RestTemplate;
import com.fairychar.security.beans.SimpleOAuth2RestTemplate;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private Gson gson = new Gson();

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
    public Object gxo() {
        this.restTemplate.getAccessToken();
        return "o";
    }

    @GetMapping("o")
    public Object o() {
        System.out.println(this.oAuth2RestTemplate);
        ResponseEntity<Object> exchange = this.oAuth2RestTemplate.exchange("https://checking.cdiisp.com:8800/uias/sso/getAccessToken", HttpMethod.POST
                , null, Object.class, Maps.newHashMap());
        this.oAuth2RestTemplate.getAccessToken();
        return this.oAuth2RestTemplate.getOAuth2ClientContext();
    }


    @GetMapping("o2")
    public Object o2() {
        System.out.println(simpleOAuth2RestTemplate);
        ResponseEntity<Object> exchange = this.simpleOAuth2RestTemplate.exchange("https://checking.cdiisp.com:8800/uias/sso/getAccessToken", HttpMethod.POST
                , null, Object.class, Maps.newHashMap());
        this.oAuth2RestTemplate.getAccessToken();
        return this.oAuth2RestTemplate.getOAuth2ClientContext();
    }

    @GetMapping("ent")
    public Object ent() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String accessToken = this.oAuth2RestTemplate.getAccessToken().getValue();

        httpHeaders.set("client_id", "gxdj");
        httpHeaders.set("accessToken", accessToken);
        httpHeaders.set("authenticator", "9VvJvgaImYgC61Eyx3g0fFtMn0Yu1FdijU43BgPF5ZP8DhYpUmM9Fhye9NdV2C8t5nFz8nsOznonK+DDPDFgBg==");
        httpHeaders.set("timeStamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        PageRequest pageRequest = new PageRequest(100, 1);
        String json = gson.toJson(pageRequest);
        String url = "https://checking.cdiisp.com:8800/uias/ssoOrgUser/getEntInfo";
        ResponseEntity<Object> objectResponseEntity = this.oAuth2RestTemplate.postForEntity(url
                , new HttpEntity<>(pageRequest), Object.class, Maps.newHashMap());
        HttpEntity httpEntity = new HttpEntity(pageRequest,httpHeaders);
        HttpEntity httpEntityJson = new HttpEntity(json,httpHeaders);
        ResponseEntity<Object> exchange = this.oAuth2RestTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class, Maps.newHashMap());
        System.out.println(exchange);
        ResponseEntity<Object> exchangeJson = this.oAuth2RestTemplate.exchange(url, HttpMethod.POST, httpEntityJson, Object.class, Maps.newHashMap());
        System.out.println(exchangeJson);
        System.out.println(objectResponseEntity.getBody());
        return objectResponseEntity;
    }

    @GetMapping("ent1")
    public Object ent1() {
        PageRequest pageRequest = new PageRequest(100, 1);
        String url = "https://checking.cdiisp.com:8800/uias/ssoOrgUser/getEntInfo";
        ResponseEntity<Object> objectResponseEntity = this.oAuth2RestTemplate.postForEntity(url
                , new HttpEntity<>(pageRequest), Object.class, Maps.newHashMap());
        HttpEntity httpEntity = new HttpEntity(pageRequest);
        ResponseEntity<Object> exchange = this.oAuth2RestTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class, Maps.newHashMap());
        System.out.println(exchange);
        return objectResponseEntity;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class PageRequest {
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