package com.zhopy.statusservice.validator;

import com.zhopy.statusservice.dto.StatusRequest;
import com.zhopy.statusservice.utils.exeptions.ApiNotFound;
import com.zhopy.statusservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

@Service
public interface IStatusValidator {
    void validator(StatusRequest statusRequest) throws ApiUnprocessableEntity;

    void validatorById(Long statusCode) throws ApiNotFound;

    void validatorUpdate(StatusRequest request) throws ApiUnprocessableEntity;

    void validatorByIdRequest(Long urlCode, Long statusCode) throws ApiNotFound, ApiUnprocessableEntity;
}
