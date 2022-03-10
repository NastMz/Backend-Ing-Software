package com.zhopy.orderservice.validator;

import com.zhopy.orderservice.dto.OrderRequest;
import com.zhopy.orderservice.utils.exeptions.ApiNotFound;
import com.zhopy.orderservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de compras
@Service
public interface IOrderValidator {
    void validator(OrderRequest orderRequest) throws ApiUnprocessableEntity;

    void validatorById(Long orderNumber) throws ApiNotFound;

    void validatorUpdate(OrderRequest request) throws ApiUnprocessableEntity;

    void validatorByIdRequest(Long urlCode, Long orderNumber) throws ApiNotFound, ApiUnprocessableEntity;
}
