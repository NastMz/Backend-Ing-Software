package com.zhopy.cityservice.validator;


import com.zhopy.cityservice.dto.CityDTO;
import com.zhopy.cityservice.dto.CityRequest;
import com.zhopy.cityservice.service.interfaces.ICityService;
import com.zhopy.cityservice.utils.exeptions.ApiNotFound;
import com.zhopy.cityservice.utils.exeptions.ApiUnprocessableEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CityValidator")
public class CityValidatorImplement implements ICityValidator {

    @Autowired
    @Qualifier("CityService")
    private ICityService cityService;

    @Override
    public void validator(CityRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        if (cityService.existsByCityName(request.getCityName())) {
            this.message422("The name already exists");
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
            this.message404("The code does not exist");
        }
    }

    @Override
    public void validatorByIdRequest(Long urlCode, Long roleCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!cityService.existsByCityCode(urlCode)) {
            this.message404("The code does not exist");
        }
        if (!urlCode.equals(roleCode)) {
            this.message422("The request body code does not match the uri code");
        }
    }

    private void validateData(CityRequest request) throws ApiUnprocessableEntity {
        if (request.getCityName() == null || StringUtils.isBlank(request.getCityName())) {
            this.message422("The name cannot be empty");
        }
    }

    private void validateName(CityRequest roleRequest) throws ApiUnprocessableEntity {
        CityDTO citySearch = this.cityService.findByCityCode(roleRequest.getCityCode());
        if (!roleRequest.getCityName().equals(citySearch.getCityName()) && cityService.existsByCityName(roleRequest.getCityName())) {
            this.message422("The name already exists");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
