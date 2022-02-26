package com.zhopy.shoesservice.validator;

import com.zhopy.shoesservice.dto.ShoesRequest;
import com.zhopy.shoesservice.utils.exeptions.ApiNotFound;
import com.zhopy.shoesservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

// Interface para la validacion de datos recibidos para la creacion de zapatos
@Service
public interface IShoesValidator {
    void validator(ShoesRequest shoesRequest) throws ApiUnprocessableEntity;

    void validatorById(String shoeCode) throws ApiNotFound;

    void validatorByIdRequest(String urlCode, String shoeCode) throws ApiNotFound, ApiUnprocessableEntity;

    void validatorUpdate(ShoesRequest request) throws ApiUnprocessableEntity;


}
