package com.zhopy.cityservice.validator;


import com.zhopy.cityservice.dto.CityRequest;
import com.zhopy.cityservice.service.interfaces.ICityService;
import com.zhopy.cityservice.utils.exeptions.ApiNotFound;
import com.zhopy.cityservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityValidatorImplement implements CityValidator {

    @Autowired
    private ICityService cityService;

    @Override
    public void validator(CityRequest request) throws ApiUnprocessableEntity {
        if (cityService.existsByCityCode(request.getCityCode())) {
            this.message422("El codigo ya existe");
        }
        if (cityService.existsByCityName(request.getCityName())){
            this.message422("El nombre ya existe");
        }
    }

    @Override
    public void validatorById(Long id) throws ApiNotFound {
        if (!cityService.existsByCityCode(id)){
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
