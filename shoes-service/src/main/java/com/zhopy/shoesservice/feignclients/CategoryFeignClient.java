package com.zhopy.shoesservice.feignclients;

import com.zhopy.shoesservice.model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service")
public interface CategoryFeignClient {

    @GetMapping(value = "/api/category/detail/{categoryCode}")
    Category findByCategoryCode(@PathVariable("categoryCode") Long categoryCode);

    @GetMapping("/api/category/validate/{categoryCode}")
    boolean existsByCategoryCode(@PathVariable("categoryCode") Long categoryCode);
}
