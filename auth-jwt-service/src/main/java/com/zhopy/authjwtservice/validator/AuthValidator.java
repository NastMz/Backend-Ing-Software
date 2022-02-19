package com.zhopy.authjwtservice.validator;

import com.zhopy.authjwtservice.exceptions.ApiUnauthorized;
import com.zhopy.authjwtservice.utils.exceptions.ApiNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Component
public class AuthValidator {

    @Autowired
    UserValidator userValidator;

    private static final String CLIENT_CREDENTIALS = "client_credentials";

    public void validate(MultiValueMap<String, String> paramMap, String grantType) throws ApiUnauthorized {
        if (grantType.isEmpty() || !grantType.equals(CLIENT_CREDENTIALS)) {
            message("El campo grant_type es invalido");
        }

        if (Objects.isNull(paramMap) || paramMap.getFirst("email").isEmpty() || paramMap.getFirst("password").isEmpty()) {
            message("client_id y/o client_secret son invalidos");
        }
    }

    public void search(MultiValueMap<String, String> paramMap) throws ApiNotFound {
        if (!userValidator.validatorCredentials(paramMap.getFirst("email"), paramMap.getFirst("password") )){
            message404("Credenciales no validas");
        }
    }

    private void message(String message) throws ApiUnauthorized {
        throw new ApiUnauthorized(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
