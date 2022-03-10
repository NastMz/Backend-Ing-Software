package com.zhopy.buyservice.feignclients;

import com.zhopy.buyservice.model.ShoesBoughtRequests;
import com.zhopy.buyservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shoesbought-service")
public interface ShoesBoughtFeignClient {
    @PostMapping("/api/shoesbought/save")
    ResponseEntity<Object> save(@RequestBody ShoesBoughtRequests shoesBoughtRequest) throws ApiUnprocessableEntity;
}
