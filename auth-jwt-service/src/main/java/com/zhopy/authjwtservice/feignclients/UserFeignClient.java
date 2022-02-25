package com.zhopy.authjwtservice.feignclients;

import com.zhopy.authjwtservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service", url = "http://localhost:8000")
public interface UserFeignClient {
    @PostMapping("/api/user/validate")
    boolean validatorCredentials(User user);

    @PostMapping(value = "/api/user/find")
    User findByEmail(User user);
}
