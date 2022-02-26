package com.zhopy.roleservice.validator;

import com.zhopy.roleservice.dto.RoleRequest;
import com.zhopy.roleservice.utils.exeptions.ApiNotFound;
import com.zhopy.roleservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de usuarios
@Service
public interface IRoleValidator {
    void validator(RoleRequest roleRequest) throws ApiUnprocessableEntity;

    void validatorById(Long roleCode) throws ApiNotFound;

    void validatorUpdate(RoleRequest request) throws ApiUnprocessableEntity;

    void validatorByIdRequest(Long urlCode, Long roleCode) throws ApiNotFound, ApiUnprocessableEntity;
}
