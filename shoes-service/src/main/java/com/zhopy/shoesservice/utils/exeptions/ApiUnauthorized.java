package com.zhopy.shoesservice.utils.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Excepcion personalizada de estatus 401
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApiUnauthorized extends Exception {
    public ApiUnauthorized(String message) {
        super(message);
    }
}
