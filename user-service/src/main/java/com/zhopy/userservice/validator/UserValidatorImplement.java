package com.zhopy.userservice.validator;

import com.zhopy.userservice.dto.UserRequest;
import com.zhopy.userservice.service.interfaces.IUserService;
import com.zhopy.userservice.utils.exceptions.ApiNotFound;
import com.zhopy.userservice.utils.exceptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImplement implements UserValidator {

    @Autowired
    private IUserService userService;

    @Override
    public void validator(UserRequest request) throws ApiUnprocessableEntity {
        if (userService.existsByUserId(request.getUserId())){
            this.message422("El documento ya existe");
        }
        if (userService.existsByEmail(request.getEmail())){
            this.message422("El correo ya existe");
        }
    }

    @Override
    public void validatorById(String id) throws ApiNotFound {
        if (!userService.existsByUserId(id)){
            this.message404("El documento no existe");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
