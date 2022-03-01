package com.zhopy.authjwtservice.validator;

import com.zhopy.authjwtservice.exceptions.ApiUnauthorized;
import com.zhopy.authjwtservice.model.UserValidate;
import com.zhopy.authjwtservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Component
public class AuthValidator {

    @Autowired
    AuthService authService;

    private static final String CLIENT_CREDENTIALS = "client_credentials";

    public void validate(MultiValueMap<String, String> paramMap, String grantType) throws ApiUnauthorized {
        if (grantType.isEmpty() || !grantType.equals(CLIENT_CREDENTIALS)) {
            message("The grant_type field is invalid");
        }

        if (Objects.isNull(paramMap) || paramMap.getFirst("email").isEmpty() || paramMap.getFirst("password").isEmpty()) {
            message("client_id and/or client_secret are invalid");
        }

        UserValidate userValidate = new UserValidate(paramMap.getFirst("email"), paramMap.getFirst("password"));
        try {
            if (!authService.validatorCredentials(userValidate)) {
                message("Invalid credentials");
            }
        } catch (Exception e) {
            message("Invalid credentials");
        }

    }

    private void message(String message) throws ApiUnauthorized {
        throw new ApiUnauthorized(message);
    }
}
