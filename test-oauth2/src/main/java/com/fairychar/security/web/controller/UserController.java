package com.fairychar.security.web.controller;

import com.fairychar.security.beans.GxOAuth2RestTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return this.oAuth2RestTemplate.getOAuth2ClientContext();
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