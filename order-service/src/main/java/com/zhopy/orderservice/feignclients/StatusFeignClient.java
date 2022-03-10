package com.zhopy.orderservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "status-service")
public interface StatusFeignClient {
    @PostMapping("/api/status/exists/{statusCode}")
    boolean existsByStatusCode(@PathVariable("statusCode") Long statusCode);
}
