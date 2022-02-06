package com.zhopy.userservice.validator;

import com.zhopy.userservice.dto.UserRequest;
import com.zhopy.userservice.utils.exceptions.ApiNotFound;
import com.zhopy.userservice.utils.exceptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de usuarios
@Service
public interface UserValidator {
    void validator(UserRequest userRequest) throws ApiUnprocessableEntity;
    void validatorById(String userId) throws ApiNotFound;
}
