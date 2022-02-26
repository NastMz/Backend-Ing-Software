package com.zhopy.userservice.validator;

import com.zhopy.userservice.dto.UserDTO;
import com.zhopy.userservice.dto.UserRequest;
import com.zhopy.userservice.service.interfaces.IUserService;
import com.zhopy.userservice.utils.exceptions.ApiNotFound;
import com.zhopy.userservice.utils.exceptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("UserValidator")
public class UserValidatorImplement implements IUserValidator {

    @Autowired
    @Qualifier("UserService")
    private IUserService userService;

    @Override
    public void validator(UserRequest request) throws ApiUnprocessableEntity {
        if (request.getUserId() == null || request.getUserId().isBlank()) {
            this.message422("El documento no puede estar vacio");
        }
        validateData(request);
        if (userService.existsByUserId(request.getUserId())) {
            this.message422("El documento ya existe");
        }
        if (userService.existsByEmail(request.getEmail())) {
            this.message422("El correo ya existe");
        }
    }

    @Override
    public void validatorUpdate(UserRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        validateEmail(request);
    }

    @Override
    public void validatorById(String userId) throws ApiNotFound {
        if (!userService.existsByUserId(userId)) {
            this.message404("El documento no existe");
        }
    }

    @Override
    public void validatorByIdRequest(String urlId, String userId) throws ApiNotFound, ApiUnprocessableEntity {
        if (!userService.existsByUserId(urlId)) {
            this.message404("El documento no existe");
        }
        if (!urlId.equals(userId)) {
            this.message422("El documento del cuerpo de la peticion no coincide con el de la uri");
        }
    }

    @Override
    public void validatorByEmail(String email) throws ApiNotFound {
        if (!userService.existsByEmail(email)) {
            this.message404("El correo no existe");
        }
    }

    private void validateData(UserRequest request) throws ApiUnprocessableEntity {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            this.message422("El correo no puede estar vacio");
        }
        if (request.getAddress() == null || request.getAddress().isBlank()) {
            this.message422("La direccion no puede estar vacia");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            this.message422("La contraseña no puede estar vacia");
        }
        if (request.getPhone() == null || request.getPhone().isBlank()) {
            this.message422("El telefono no puede estar vacio");
        }
        if (request.getUserName() == null || request.getUserName().isBlank()) {
            this.message422("El nombre no puede estar vacio");
        }
        if (!userService.existsByRoleCode(request.getRoleCode())) {
            this.message422("El codigo de rol no existe");
        }
    }

    private void validateEmail(UserRequest userRequest) throws ApiUnprocessableEntity {
        UserDTO userSearch = this.userService.findByUserId(userRequest.getUserId());
        if (!userRequest.getEmail().equals(userSearch.getEmail()) && userService.existsByEmail(userRequest.getEmail())) {
            this.message422("El correo ya existe");
        }
    }


    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
