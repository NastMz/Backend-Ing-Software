package com.zhopy.shoesservice.validator;

import com.zhopy.shoesservice.dto.ShoesRequest;
import com.zhopy.shoesservice.service.interfaces.IShoesService;
import com.zhopy.shoesservice.utils.exeptions.ApiNotFound;
import com.zhopy.shoesservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoesValidatorImplement implements ShoesValidator {

    @Autowired
    private IShoesService shoeService;

    @Override
    public void validator(ShoesRequest request) throws ApiUnprocessableEntity {
        if (shoeService.existsByShoeCode(request.getShoeCode())) {
            this.message422("El codigo ya existe");
        }
        if (shoeService.existsByShoeName(request.getShoeName())) {
            this.message422("El nombre ya existe");
        }
    }

    @Override
    public void validatorById(String id) throws ApiNotFound {
        if (!shoeService.existsByShoeCode(id)) {
            this.message404("El codigo no existe");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
