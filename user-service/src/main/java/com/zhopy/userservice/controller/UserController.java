package com.zhopy.userservice.controller;

import com.zhopy.userservice.dto.UserDTO;
import com.zhopy.userservice.dto.UserRequest;
import com.zhopy.userservice.dto.UserValidate;
import com.zhopy.userservice.entity.User;
import com.zhopy.userservice.service.interfaces.IUserService;
import com.zhopy.userservice.utils.exceptions.ApiNotFound;
import com.zhopy.userservice.utils.exceptions.ApiUnprocessableEntity;
import com.zhopy.userservice.utils.hash.BCrypt;
import com.zhopy.userservice.utils.helpers.MapperHelper;
import com.zhopy.userservice.validator.UserValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "user-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    @Qualifier("UserService")
    private IUserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping(value = "/detail/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByUserId(@PathVariable("userId") String userId) throws ApiNotFound {
        this.userValidator.validatorById(userId);
        return ResponseEntity.ok(this.userService.findByUserId(userId));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody UserRequest userRequest) throws ApiUnprocessableEntity {
        this.userValidator.validator(userRequest);
        this.userService.save(userRequest);
        return ResponseEntity.ok("El usuario se guardo correctamente");
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> update(@PathVariable("userId") String userId, @RequestBody UserRequest userRequest) throws ApiNotFound, ApiUnprocessableEntity {
        this.userValidator.validatorByIdRequest(userId, userRequest.getUserId());
        this.userValidator.validatorUpdate(userRequest);
        this.userService.update(userRequest, userId);
        return ResponseEntity.ok("El usuario se actualizo correctamente");
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<Object> delete(@PathVariable("userId") String userId) throws ApiNotFound {
        this.userValidator.validatorById(userId);
        this.userService.delete(userId);
        return ResponseEntity.ok("El usuario se elimino correctamente");
    }

    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validatorCredentials(@RequestBody UserValidate userValidate) throws ApiNotFound {
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
    public ResponseEntity<Object> findByEmail(@RequestBody UserValidate userValidate) throws ApiNotFound {
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

}
