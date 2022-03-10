package com.zhopy.shoesboughtservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "buy-service")
public interface BuyFeignClient {
    @PostMapping("/api/buy/exists/{buyNumber}")
    boolean existsByBuyNumber(@PathVariable("buyNumber") Long buyNumber);
}
