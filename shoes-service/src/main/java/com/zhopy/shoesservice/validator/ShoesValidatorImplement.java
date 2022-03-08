package com.zhopy.shoesservice.validator;

import com.zhopy.shoesservice.dto.ShoesDTO;
import com.zhopy.shoesservice.dto.ShoesRequest;
import com.zhopy.shoesservice.service.interfaces.IShoesService;
import com.zhopy.shoesservice.utils.exeptions.ApiNotFound;
import com.zhopy.shoesservice.utils.exeptions.ApiUnprocessableEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("ShoesValidator")
public class ShoesValidatorImplement implements IShoesValidator {

    @Autowired
    @Qualifier("ShoesService")
    private IShoesService shoeService;

    @Override
    public void validator(ShoesRequest request) throws ApiUnprocessableEntity {
        if (request.getShoeCode() == null || request.getShoeCode())){
            this.message422("The code cannot be empty");
        }
        validateData(request);
        if (shoeService.existsByShoeCode(request.getShoeCode())) {
            this.message422("The code already exists");
        }
        if (shoeService.existsByShoeName(request.getShoeName())) {
            this.message422("The name already exists");
        }
    }

    @Override
    public void validatorUpdate(ShoesRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        validateName(request);
    }

    @Override
    public void validatorById(String id) throws ApiNotFound {
        if (!shoeService.existsByShoeCode(id)) {
            this.message404("The code does not exist");
        }
    }

    @Override
    public void validatorByIdRequest(String urlCode, String shoeCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!shoeService.existsByShoeCode(urlCode)) {
            this.message404("The code does not exist");
        }
        if (!urlCode.equals(shoeCode)) {
            this.message422("The request body code does not match the uri code");
        }
    }

    private void validateData(ShoesRequest request) throws ApiUnprocessableEntity {
        if (request.getShoeName() == null || StringUtils.isBlank(request.getShoeName())) {
            this.message422("The name cannot be empty");
        }
        if (request.getPrice() == null || StringUtils.isBlank(request.getPrice())) {
            this.message422("The price cannot be empty");
        }
        if (request.getStock() == null || StringUtils.isBlank(request.getStock())) {
            this.message422("The stock cannot be empty");
        }
        if (request.getDescription() == null || StringUtils.isBlank(request.getDescription())) {
            this.message422("The description cannot be empty");
        }
        if (!shoeService.existsByCategoryCode(request.getCategoryCode())) {
            this.message422("The category does not exist");
        }
        if (!shoeService.existsBySupplierNit(request.getSupplierNit())) {
            this.message422("The supplier does not exist");
        }
    }

    private void validateName(ShoesRequest shoeRequest) throws ApiUnprocessableEntity {
        ShoesDTO shoeSearch = this.shoeService.findByShoeCode(shoeRequest.getShoeCode());
        if (!shoeRequest.getShoeName().equals(shoeSearch.getShoeName()) && shoeService.existsByShoeName(shoeRequest.getShoeName())) {
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
