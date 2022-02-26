package com.zhopy.buyservice.validator;

import com.zhopy.buyservice.dto.BuyRequest;
import com.zhopy.buyservice.utils.exeptions.ApiNotFound;
import com.zhopy.buyservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de compras
@Service
public interface BuyValidator {
    void validator(BuyRequest buyRequest) throws ApiUnprocessableEntity;

    void validatorById(Long buyNumber) throws ApiNotFound;

    void validatorUpdate(BuyRequest request) throws ApiUnprocessableEntity;

    void validatorByIdRequest(Long urlCode, Long buyNumber) throws ApiNotFound, ApiUnprocessableEntity;
}
