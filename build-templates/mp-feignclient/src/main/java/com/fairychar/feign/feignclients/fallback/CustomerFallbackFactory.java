package com.fairychar.feign.feignclients.fallback;

import com.fairychar.bag.proxy.FeignFallbackProxy;
import com.fairychar.feign.feignclients.CustomerFeignClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户表(Customer)表服务接口类
 *
 * @author chiyo
 * @since 2021-05-27 20:56:54
 */
@Component
public class CustomerFallbackFactory implements FallbackFactory<CustomerFeignClient> {
    @Override
    public CustomerFeignClient create(Throwable throwable) {
        return FeignFallbackProxy.createDefault(CustomerFeignClient.class, throwable);
    }
}


