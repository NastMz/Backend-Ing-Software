package com.zhopy.shoesservice.utils.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Excepcion personalizada de estatus 404
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiNotFound extends Exception {
    public ApiNotFound(String message) {
        super(message);
    }
}
