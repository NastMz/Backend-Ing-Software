package com.zhopy.shoesservice.feignclients;

import com.zhopy.shoesservice.model.Supplier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "supplier-service")
public interface SupplierFeignClient {
    @GetMapping(value = "/api/supplier/detail/{supplierNit}")
    Supplier findBySupplierNit(@PathVariable("supplierNit") String supplierNit);

    @GetMapping("/api/supplier/validate/{supplierNit}")
    boolean existsBySupplierNit(@PathVariable("supplierNit") String supplierNit);
}
