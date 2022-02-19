package com.zhopy.cityservice.validator;

import com.zhopy.cityservice.dto.CityRequest;
import com.zhopy.cityservice.utils.exeptions.ApiNotFound;
import com.zhopy.cityservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de usuarios
@Service
public interface CityValidator {
    void validator(CityRequest cityRequest) throws ApiUnprocessableEntity;

    void validatorById(Long cityCode) throws ApiNotFound;

    void validatorUpdate(CityRequest request) throws ApiUnprocessableEntity;

    void validatorByIdRequest(Long urlCode, Long roleCode) throws ApiNotFound, ApiUnprocessableEntity;
}
