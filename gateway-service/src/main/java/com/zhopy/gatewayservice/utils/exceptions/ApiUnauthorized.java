package com.zhopy.gatewayservice.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.ClientResponse;

// Excepcion personalizada de estatus 401
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApiUnauthorized extends Exception {
    public ApiUnauthorized(ClientResponse clientResponse) {
        super("Token no valido");
    }
}
