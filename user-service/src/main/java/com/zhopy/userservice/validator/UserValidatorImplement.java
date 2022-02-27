package com.zhopy.userservice.validator;

import com.zhopy.userservice.dto.UserDTO;
import com.zhopy.userservice.dto.UserRequestRegister;
import com.zhopy.userservice.dto.UserRequestUpdate;
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
    public void validator(UserRequestRegister request) throws ApiUnprocessableEntity {
        if (request.getUserId() == null || request.getUserId().isBlank()) {
            this.message422("El documento no puede estar vacio");
        }
        validateDataRegister(request);
        if (userService.existsByUserId(request.getUserId())) {
            this.message422("El documento ya existe");
        }
        if (userService.existsByEmail(request.getEmail())) {
            this.message422("El correo ya existe");
        }
    }

    @Override
    public void validatorUpdate(UserRequestUpdate request) throws ApiUnprocessableEntity {
        validateDataUpdate(request);
        validateEmail(request.getUserId(), request.getEmail());
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

    private void validateDataRegister(UserRequestRegister request) throws ApiUnprocessableEntity {
        validateData(request.getEmail(), request.getAddress(), request.getPassword(), request.getPhone(), request.getUserName());
        if (request.getRoleCode() == null || request.getRoleCode().toString().isBlank()) {
            this.message422("El codigo de rol no puede estar vacio");
        }
        if (!userService.existsByRoleCode(request.getRoleCode())) {
            this.message422("El codigo de rol no existe");
        }
        if (request.getQuestionCode() == null || request.getQuestionCode().toString().isBlank()) {
            this.message422("El codigo de pregunta no puede estar vacio");
        }
        if (!userService.existsByQuestionCode(request.getQuestionCode())) {
            this.message422("El codigo de pregunta no existe");
        }
        if (request.getSecureAnswer() == null || request.getSecureAnswer().isBlank()) {
            this.message422("La repuesta de la pregunta no puede estar vacia");
        }
    }

    private void validateDataUpdate(UserRequestUpdate request) throws ApiUnprocessableEntity {
        validateData(request.getEmail(), request.getAddress(), request.getPassword(), request.getPhone(), request.getUserName());
    }

    private void validateData(String email, String address, String password, String phone, String userName) throws ApiUnprocessableEntity {
        if (email == null || email.isBlank()) {
            this.message422("El correo no puede estar vacio");
        }
        if (address == null || address.isBlank()) {
            this.message422("La direccion no puede estar vacia");
        }
        if (password == null || password.isBlank()) {
            this.message422("La contraseña no puede estar vacia");
        }
        if (phone == null || phone.isBlank()) {
            this.message422("El telefono no puede estar vacio");
        }
        if (userName == null || userName.isBlank()) {
            this.message422("El nombre no puede estar vacio");
        }
    }

    private void validateEmail(String userId, String email) throws ApiUnprocessableEntity {
        UserDTO userSearch = this.userService.findByUserId(userId);
        if (!email.equals(userSearch.getEmail()) && userService.existsByEmail(email)) {
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
