package com.zhopy.userservice.feignclients;

import com.zhopy.userservice.model.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "role-service")
public interface RoleFeignClient {

    @GetMapping(value = "/api/role/detail/{roleCode}")
    Role findByRoleCode(@PathVariable("roleCode") Long roleCode);

    @GetMapping("/api/role/validate/{roleCode}")
    boolean existsByRoleCode(@PathVariable("roleCode") Long roleCode);
}
