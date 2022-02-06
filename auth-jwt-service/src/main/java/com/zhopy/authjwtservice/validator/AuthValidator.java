package com.zhopy.authjwtservice.validator;

import com.zhopy.authjwtservice.exceptions.ApiUnauthorized;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Component
public class AuthValidator {
    private static final String CLIENT_CREDENTIALS = "client_credentials";

    public void validate(MultiValueMap<String, String> paramMap, String grantType) throws ApiUnauthorized {
        if (grantType.isEmpty() || !grantType.equals(CLIENT_CREDENTIALS)) {
            message("El campo grant_type es invalido");
        }

        if (Objects.isNull(paramMap) || paramMap.getFirst("email").isEmpty() || paramMap.getFirst("password").isEmpty()) {
            message("client_id y/o client_secret son invalidos");
        }
    }

    private void message(String message) throws ApiUnauthorized {
        throw new ApiUnauthorized(message);
    }
}
