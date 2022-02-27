package com.zhopy.authjwtservice.feignclients;

import com.zhopy.authjwtservice.model.User;
import com.zhopy.authjwtservice.model.UserValidate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @PostMapping("/api/user/validate")
    boolean validatorCredentials(UserValidate userValidate);

    @PostMapping(value = "/api/user/find")
    User findByEmail(UserValidate userValidate);
}
