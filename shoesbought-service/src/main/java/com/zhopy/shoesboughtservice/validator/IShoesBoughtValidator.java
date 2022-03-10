package com.zhopy.shoesboughtservice.validator;

import com.zhopy.shoesboughtservice.dto.ShoesBoughtRequests;
import com.zhopy.shoesboughtservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

@Service
public interface IShoesBoughtValidator {
    void validator(ShoesBoughtRequests shoesRequest) throws ApiUnprocessableEntity;
}