package com.zhopy.userservice.validator;

import com.zhopy.userservice.dto.UserRequest;
import com.zhopy.userservice.utils.exceptions.ApiNotFound;
import com.zhopy.userservice.utils.exceptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de usuarios
@Service
public interface UserValidator {
    void validator(UserRequest userRequest) throws ApiUnprocessableEntity;

    void validatorUpdate(UserRequest request) throws ApiUnprocessableEntity;

    void validatorById(String userId) throws ApiNotFound;

    void validatorByIdRequest(String urlId, String userId) throws ApiNotFound, ApiUnprocessableEntity;

    void validatorByEmail(String email) throws ApiNotFound;
}
