package com.zhopy.userservice.validator;

import com.zhopy.userservice.dto.UserRequestRegister;
import com.zhopy.userservice.dto.UserRequestUpdate;
import com.zhopy.userservice.dto.UserRestore;
import com.zhopy.userservice.utils.exceptions.ApiNotFound;
import com.zhopy.userservice.utils.exceptions.ApiUnauthorized;
import com.zhopy.userservice.utils.exceptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de usuarios
@Service
public interface IUserValidator {
    void validator(UserRequestRegister userRequestRegister) throws ApiUnprocessableEntity;

    void validatorUpdate(UserRequestUpdate request) throws ApiUnprocessableEntity;

    void validatorById(String userId) throws ApiNotFound;

    void validatorByIdRequest(String urlId, String userId) throws ApiNotFound, ApiUnprocessableEntity;

    void validatorByEmail(String email) throws ApiNotFound, ApiUnprocessableEntity;

    void validatorSecureAnswer(String email, String secureAnswer) throws ApiUnauthorized, ApiUnprocessableEntity, ApiNotFound;

    void validatorRestore(UserRestore userRestore) throws ApiUnauthorized, ApiUnprocessableEntity, ApiNotFound;
}
