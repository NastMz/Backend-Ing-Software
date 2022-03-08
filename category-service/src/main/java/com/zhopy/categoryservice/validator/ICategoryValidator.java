package com.zhopy.categoryservice.validator;

import com.zhopy.categoryservice.dto.CategoryRequest;
import com.zhopy.categoryservice.utils.exeptions.ApiNotFound;
import com.zhopy.categoryservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de usuarios
@Service
public interface ICategoryValidator {
    void validator(CategoryRequest categoryRequest) throws ApiUnprocessableEntity;

    void validatorById(Long categoryCode) throws ApiNotFound;

    void validatorUpdate(CategoryRequest request) throws ApiUnprocessableEntity;

    void validatorByIdRequest(Long urlCode, Long categoryCode) throws ApiNotFound, ApiUnprocessableEntity;
}
