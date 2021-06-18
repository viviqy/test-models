package com.fairychar.webtest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Datetime: 2020/12/15 13:20 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@RestController
@RequestMapping("/index")
public class IndexController {
    private int counter = 0;

    @RequestMapping("/hi")
    public Map<String, Object> hi() {
        return new HashMap<String, Object>() {{
            put("word", "hi");
        }};
    }

    @PostMapping("/name")
    public String name(@RequestBody Customer customer) {
        System.out.println(customer);
        return customer.getName();
    }

    /**
     * 当请求书大于server.tomcat.max-threads时会阻塞,造成外部http请求无法响应
     * @return
     */
    @GetMapping("block")
    public String block() {
        System.out.println(Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return counter++ + "";
    }


    /**
     * 及时server.tomcat.max-threads=1时,也能不停接收请求并异步响应,不会阻塞io线程
     * @return
     */
    @GetMapping("sync")
    public DeferredResult<String> sync(){
        DeferredResult<String> deferredResult = new DeferredResult<String>(10_000L,()-> "超时了");
        System.out.println("out: "+Thread.currentThread().getName());
        ForkJoinPool.commonPool().submit(() -> {
            System.out.println("inner: "+Thread.currentThread().getName());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
            }
            deferredResult.setResult("ok");
        });
        return deferredResult;
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    static class Customer {
        private String name;
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