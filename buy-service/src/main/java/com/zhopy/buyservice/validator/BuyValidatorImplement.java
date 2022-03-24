package com.zhopy.buyservice.validator;

import com.zhopy.buyservice.dto.BuyRequest;
import com.zhopy.buyservice.model.ShoesBought;
import com.zhopy.buyservice.service.interfaces.IBuyService;
import com.zhopy.buyservice.utils.exeptions.ApiNotFound;
import com.zhopy.buyservice.utils.exeptions.ApiUnprocessableEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("BuyValidator")
public class BuyValidatorImplement implements IBuyValidator {

    @Autowired
    @Qualifier("BuyService")
    private IBuyService buyService;

    @Override
    public void validatorById(Long id) throws ApiNotFound {
        if (!buyService.existsByBuyNumber(id)) {
            this.message404("The purchase number does not exist");
        }
    }

    @Override
    public void validator(BuyRequest request) throws ApiUnprocessableEntity {
        validateData(request);
    }
    
    @Override
    public void validatorUpdate(BuyRequest request) throws ApiUnprocessableEntity {
        validateData(request);
    }

    private void validateData(BuyRequest request) throws ApiUnprocessableEntity {
        if (request.getTotal() == null) {
            this.message422("Total cannot be empty");
        }
        if (request.getTotal() < 0) {
            this.message422("Total cannot be less than 0");
        }

        if (request.getUserId() == null || StringUtils.isBlank(request.getUserId())) {
            this.message422("The user document cannot be empty");
        }

        if (!buyService.existsByUserId(request.getUserId())) {
            this.message422("The user document does not exist");
        }

        Iterable<ShoesBought> shoes = request.getShoesList();
        for (ShoesBought shoe : shoes) {
            if (shoe.getShoeCode() == null || StringUtils.isBlank(shoe.getShoeCode())) {
                this.message422("The shoe code cannot be empty");
            }
            if (!buyService.existsByShoeCode(shoe.getShoeCode())) {
                this.message422("The shoe code " + shoe.getShoeCode() + " does not exist");
            }
        }
    }

    @Override
    public void validatorByIdRequest(Long urlCode, Long roleCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!buyService.existsByBuyNumber(urlCode)) {
            this.message404("The purchase number does not exist");
        }
        if (!urlCode.equals(roleCode)) {
            this.message422("The request body code does not match the uri code");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
