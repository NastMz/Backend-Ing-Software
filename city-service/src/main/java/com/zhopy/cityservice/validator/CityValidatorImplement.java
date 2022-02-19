package com.zhopy.cityservice.validator;


import com.zhopy.cityservice.dto.CityDTO;
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
        validateData(request);
        if (cityService.existsByCityName(request.getCityName())) {
            this.message422("El nombre ya existe");
        }
    }

    @Override
    public void validatorUpdate(CityRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        validateName(request);
    }

    @Override
    public void validatorById(Long id) throws ApiNotFound {
        if (!cityService.existsByCityCode(id)) {
            this.message404("El codigo no existe");
        }
    }

    @Override
    public void validatorByIdRequest(Long urlCode, Long roleCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!cityService.existsByCityCode(urlCode)) {
            this.message404("El codigo no existe");
        }
        if (!urlCode.equals(roleCode)) {
            this.message422("El codigo del cuerpo de la peticion no coincide con el de la uri");
        }
    }

    private void validateData(CityRequest request) throws ApiUnprocessableEntity {
        if (request.getCityName() == null || request.getCityName().isBlank()) {
            this.message422("El nombre no puede estar vacio");
        }
    }

    private void validateName(CityRequest roleRequest) throws ApiUnprocessableEntity {
        CityDTO citySearch = this.cityService.findByCityCode(roleRequest.getCityCode());
        if (!roleRequest.getCityName().equals(citySearch.getCityName()) && cityService.existsByCityName(roleRequest.getCityName())) {
            this.message422("El nombre ya existe");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
