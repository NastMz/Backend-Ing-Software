package com.zhopy.supplierservice.feignclients;

import com.zhopy.supplierservice.model.City;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "city-service")
public interface CityFeignClient {
    @GetMapping("/api/city/detail/{cityCode}")
    City findByCityCode(@PathVariable("cityCode") Long cityCode);

    @GetMapping("/api/city/validate/{cityCode}")
    boolean existsByCityCode(@PathVariable("cityCode") Long cityCode);
}
