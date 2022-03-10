package com.zhopy.shoesboughtservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "shoes-service")
public interface ShoesFeignClient {
    @PostMapping("/api/shoes/exists/{shoeCode}")
    boolean existsByShoeCode(@PathVariable("shoeCode") String shoeCode);
}
