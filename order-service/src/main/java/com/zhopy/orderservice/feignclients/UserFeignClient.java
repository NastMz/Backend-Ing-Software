package com.zhopy.orderservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @PostMapping("/api/user/exists/{userId}")
    boolean existsByUserId(@PathVariable("userId") String userId);
}
