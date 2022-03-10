package com.zhopy.orderservice.validator;

import com.zhopy.orderservice.dto.OrderRequest;
import com.zhopy.orderservice.service.interfaces.IOrderService;
import com.zhopy.orderservice.utils.exeptions.ApiNotFound;
import com.zhopy.orderservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("OrderValidator")
public class OrderValidatorImplement implements IOrderValidator {

    @Autowired
    @Qualifier("OrderService")
    private IOrderService orderService;

    @Override
    public void validatorById(Long id) throws ApiNotFound {
        if (!orderService.existsByOrderNumber(id)) {
            this.message404("The order number does not exist");
        }
    }

    @Override
    public void validator(OrderRequest request) throws ApiUnprocessableEntity {
        validateData(request);
    }

    @Override
    public void validatorUpdate(OrderRequest request) throws ApiUnprocessableEntity {
        validateData(request);
    }

    private void validateData(OrderRequest request) throws ApiUnprocessableEntity {
        if (!orderService.existsByBuyNumber(request.getBuyNumber())) {
            this.message422("The buy number does not exist");
        }
        if (!orderService.existsByStatusCode(request.getStatusCode())) {
            this.message422("The status code does not exist");
        }
        if (!orderService.existsByUserId(request.getUserId())) {
            this.message422("The user document does not exist");
        }
    }

    @Override
    public void validatorByIdRequest(Long urlCode, Long roleCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!orderService.existsByOrderNumber(urlCode)) {
            this.message404("The order number does not exist");
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
