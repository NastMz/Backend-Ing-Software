package com.zhopy.authjwtservice.validator;

import com.zhopy.authjwtservice.exceptions.ApiUnauthorized;
import com.zhopy.authjwtservice.feignclients.UserFeignClient;
import com.zhopy.authjwtservice.model.UserValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Component
public class AuthValidator {

    @Autowired
    UserFeignClient userFeignClient;

    private static final String CLIENT_CREDENTIALS = "client_credentials";

    public void validate(MultiValueMap<String, String> paramMap, String grantType) throws ApiUnauthorized {
        if (grantType.isEmpty() || !grantType.equals(CLIENT_CREDENTIALS)) {
            message("El campo grant_type es invalido");
        }

        if (Objects.isNull(paramMap) || paramMap.getFirst("email").isEmpty() || paramMap.getFirst("password").isEmpty()) {
            message("client_id y/o client_secret son invalidos");
        }

        UserValidate userValidate = new UserValidate(paramMap.getFirst("email"), paramMap.getFirst("password"));
        try {
            if (!userFeignClient.validatorCredentials(userValidate)) {
                message("Credenciales no validas");
            }
        } catch (Exception e) {
            message("Credenciales no validas");
        }

    }

    private void message(String message) throws ApiUnauthorized {
        throw new ApiUnauthorized(message);
    }
}
