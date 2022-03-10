package com.zhopy.shoesboughtservice.validator;


import com.zhopy.shoesboughtservice.dto.ShoesBoughtRequests;
import com.zhopy.shoesboughtservice.entity.ShoesBought;
import com.zhopy.shoesboughtservice.service.interfaces.IShoesBoughtService;
import com.zhopy.shoesboughtservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Qualifier("ShoesBoughtValidator")
public class ShoesBoughtValidatorImplement implements IShoesBoughtValidator {

    @Autowired
    @Qualifier("ShoesBoughtService")
    private IShoesBoughtService shoesBoughtService;


    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    @Override
    public void validator(ShoesBoughtRequests shoesRequest) throws ApiUnprocessableEntity {
        Iterable<ShoesBought> shoesBoughts = new ArrayList<>(shoesRequest.getShoesList());
        for (ShoesBought shoeBought : shoesBoughts) {
            if (!shoesBoughtService.existsByShoeCode(shoeBought.getShoeCode())) {
                this.message422("The shoe code does not exist");
            }
            if (!shoesBoughtService.existsByBuyNumber(shoeBought.getBuyNumber())) {
                this.message422("The buy number does not exist");
            }
        }
    }
}
