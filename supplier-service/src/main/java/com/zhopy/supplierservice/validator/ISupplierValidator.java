package com.zhopy.supplierservice.validator;

import com.zhopy.supplierservice.dto.SupplierRequest;
import com.zhopy.supplierservice.utils.exeptions.ApiNotFound;
import com.zhopy.supplierservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de usuarios
@Service
public interface ISupplierValidator {
    void validator(SupplierRequest categoryRequest) throws ApiUnprocessableEntity;

    void validatorById(String categoryNit) throws ApiNotFound;

    void validatorUpdate(SupplierRequest request) throws ApiUnprocessableEntity;

    void validatorByIdRequest(String urlNit, String supplierNit) throws ApiNotFound, ApiUnprocessableEntity;
}
