package com.zhopy.buyservice.validator;

import com.zhopy.buyservice.dto.BuyRequest;
import com.zhopy.buyservice.service.interfaces.IBuyService;
import com.zhopy.buyservice.utils.exeptions.ApiNotFound;
import com.zhopy.buyservice.utils.exeptions.ApiUnprocessableEntity;
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
            this.message404("El numero de compra no existe");
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
            this.message422("El total no puede estar vacio");
        }
        if (request.getTotal() < 0) {
            this.message422("El total no puede ser menr que cero");
        }

        if (!buyService.existsByUserId(request.getUserId())) {
            this.message422("El codigo de usuario no existe");
        }
    }

    @Override
    public void validatorByIdRequest(Long urlCode, Long roleCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!buyService.existsByBuyNumber(urlCode)) {
            this.message404("El codigo no existe");
        }
        if (!urlCode.equals(roleCode)) {
            this.message422("El codigo del cuerpo de la peticion no coincide con el de la uri");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
