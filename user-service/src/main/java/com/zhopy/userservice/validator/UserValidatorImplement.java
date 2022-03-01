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
            this.message422("The document cannot be empty");
        }
        if (request.getUserId().length() != 10) {
            this.message422("The document must have 10 digits");
        }
        validateDataRegister(request);
        if (userService.existsByUserId(request.getUserId())) {
            this.message422("The document is already registered");
        }
        if (userService.existsByEmail(request.getEmail())) {
            this.message422("The email is already registered");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            this.message422("Password cannot be empty");
        }
        if (request.getPassword().length() < 8) {
            this.message422("The password must have a minimum of 8 characters");
        }
        if (request.getPhone().length() < 10) {
            this.message422("The phone must have a minimum of 10 digits");
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
            this.message404("The document is not registered");
        }
    }

    @Override
    public void validatorByIdRequest(String urlId, String userId) throws ApiNotFound, ApiUnprocessableEntity {
        if (!userService.existsByUserId(urlId)) {
            this.message404("The document is not registered");
        }
        if (!urlId.equals(userId)) {
            this.message422("The document in the request body does not match the document in the uri");
        }
    }

    @Override
    public void validatorByEmail(String email) throws ApiNotFound {
        if (!userService.existsByEmail(email)) {
            this.message404("The email is not registered");
        }
    }

    private void validateDataRegister(UserRequestRegister request) throws ApiUnprocessableEntity {
        validateData(request.getEmail(), request.getAddress(), request.getPhone(), request.getUserName());
        if (request.getRoleCode() == null || request.getRoleCode().toString().isBlank()) {
            this.message422("The role cannot be empty");
        }
        if (!userService.existsByRoleCode(request.getRoleCode())) {
            this.message422("Role does not exist");
        }
        if (request.getQuestionCode() == null || request.getQuestionCode().toString().isBlank()) {
            this.message422("The question cannot be empty");
        }
        if (!userService.existsByQuestionCode(request.getQuestionCode())) {
            this.message422("Question does not exist");
        }
        if (request.getSecureAnswer() == null || request.getSecureAnswer().isBlank()) {
            this.message422("The answer of the question cannot be empty");
        }
    }

    private void validateDataUpdate(UserRequestUpdate request) throws ApiUnprocessableEntity {
        validateData(request.getEmail(), request.getAddress(), request.getPhone(), request.getUserName());
    }

    private void validateData(String email, String address, String phone, String userName) throws ApiUnprocessableEntity {
        if (email == null || email.isBlank()) {
            this.message422("The email cannot be empty");
        }
        if (address == null || address.isBlank()) {
            this.message422("The address cannot be empty");
        }
        if (phone == null || phone.isBlank()) {
            this.message422("The phone cannot be empty");
        }
        if (userName == null || userName.isBlank()) {
            this.message422("The name cannot be empty");
        }
    }

    private void validateEmail(String userId, String email) throws ApiUnprocessableEntity {
        UserDTO userSearch = this.userService.findByUserId(userId);
        if (!email.equals(userSearch.getEmail()) && userService.existsByEmail(email)) {
            this.message422("The email is already registered");
        }
    }


    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
