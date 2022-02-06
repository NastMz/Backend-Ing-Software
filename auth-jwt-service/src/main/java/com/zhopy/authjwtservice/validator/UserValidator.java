package com.zhopy.authjwtservice.validator;

import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de usuarios
@Service
public interface UserValidator {
    boolean validatorCredentials(String user, String password);
}
