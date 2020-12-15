package com.fairychar.webtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Datetime: 2020/12/15 13:14 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@Controller
@RequestMapping("/rf")
public class RedirectController {
    @RequestMapping("/r/bing")
    public String rBing(){
        return "redirect:https://bing.com";
    }

    @RequestMapping("/f/bing")
    public String fBing(){
        return "forward:https://bing.com";
    }

    @RequestMapping("/r/lc")
    public String rlc(){
        return "redirect:http://localhost:8080/index/hi";
    }

    @RequestMapping("/f/lc")
    public String flc(){
        return "forward:http://localhost:8080/index/hi";
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