package com.zhopy.userservice.controller;

import com.zhopy.userservice.dto.*;
import com.zhopy.userservice.entity.User;
import com.zhopy.userservice.service.interfaces.IUserService;
import com.zhopy.userservice.utils.exceptions.ApiNotFound;
import com.zhopy.userservice.utils.exceptions.ApiUnauthorized;
import com.zhopy.userservice.utils.exceptions.ApiUnprocessableEntity;
import com.zhopy.userservice.utils.hash.BCrypt;
import com.zhopy.userservice.utils.helpers.MapperHelper;
import com.zhopy.userservice.validator.IUserValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "user-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    @Qualifier("UserService")
    private IUserService userService;

    @Autowired
    @Qualifier("UserValidator")
    private IUserValidator userValidator;


    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping(value = "/detail/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByUserId(@PathVariable("userId") String userId) throws ApiNotFound {
        this.userValidator.validatorById(userId);
        return ResponseEntity.ok(this.userService.findByUserId(userId));
    }

    @CircuitBreaker(name = "roleCB", fallbackMethod = "fallBackSave")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody UserRequestRegister userRequestRegister) throws ApiUnprocessableEntity {
        this.userValidator.validator(userRequestRegister);
        this.userService.save(userRequestRegister);
        return ResponseEntity.ok("User saved successfully");
    }

    @CircuitBreaker(name = "roleCB", fallbackMethod = "fallBackUpdate")
    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> update(@PathVariable("userId") String userId, @RequestBody UserRequestUpdate userRequestUpdate) throws ApiNotFound, ApiUnprocessableEntity {
        this.userValidator.validatorByIdRequest(userId, userRequestUpdate.getUserId());
        this.userValidator.validatorUpdate(userRequestUpdate);
        this.userService.update(userRequestUpdate, userId);
        return ResponseEntity.ok("The user was successfully updated");
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<Object> delete(@PathVariable("userId") String userId) throws ApiNotFound {
        this.userValidator.validatorById(userId);
        this.userService.delete(userId);
        return ResponseEntity.ok("The user was deleted successfully");
    }

    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validatorCredentials(@RequestBody UserValidate userValidate) throws ApiNotFound, ApiUnprocessableEntity {
        boolean auth = false;
        String email = userValidate.getEmail();
        this.userValidator.validatorByEmail(email);
        if (userService.existsByEmail(email)) {
            User user = userService.findByEmail(email);
            if (BCrypt.checkpw(userValidate.getPassword(), user.getPassword())) {
                auth = true;
            }
        }
        return ResponseEntity.ok(auth);
    }

    @PostMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByEmail(@RequestBody UserValidate userValidate) throws ApiNotFound, ApiUnprocessableEntity {
        String email = userValidate.getEmail();
        this.userValidator.validatorByEmail(email);
        User user = userService.findByEmail(email);
        UserDTO userDTO = MapperHelper.modelMapper().map(user, UserDTO.class);
        //userDTO.setRoleName(userService.findByRoleCode(user.getRoleCode()).getRoleName());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/exists/{userId}")
    public ResponseEntity<Object> existsByUserId(@PathVariable("userId") String userId) throws ApiNotFound {
        this.userValidator.validatorById(userId);
        return ResponseEntity.ok(userService.existsByUserId(userId));
    }

    @PostMapping(value = "/check/email", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> checkEmail(@RequestBody String email) throws ApiUnprocessableEntity, ApiNotFound {
        this.userValidator.validatorByEmail(email);
        return ResponseEntity.ok("The user is registered");
    }

    @PostMapping(value = "/check/answer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> checkAnswer(@RequestBody MultiValueMap<String, String> paramMap) throws ApiUnprocessableEntity, ApiUnauthorized, ApiNotFound {
        this.userValidator.validatorSecureAnswer(paramMap.getFirst("email"), paramMap.getFirst("secureAnswer"));
        return ResponseEntity.ok("The answer is correct");
    }

    @PutMapping(value = "/restore", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> recovery(@RequestBody UserRestore userRestore) throws ApiNotFound, ApiUnprocessableEntity, ApiUnauthorized {
        this.userValidator.validatorRestore(userRestore);
        this.userService.restore(userRestore);
        return ResponseEntity.ok("The password was successfully restored");
    }

    private ResponseEntity<Object> fallBackSave(@RequestBody UserRequestRegister userRequestRegister, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem");
    }

    private ResponseEntity<Object> fallBackUpdate(@PathVariable("userId") String userId, @RequestBody UserRequestUpdate userRequestUpdate, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem");
    }

}
