package com.zhopy.userservice.controller;

import com.zhopy.userservice.dto.UserRequest;
import com.zhopy.userservice.service.interfaces.IUserService;
import com.zhopy.userservice.utils.exceptions.ApiNotFound;
import com.zhopy.userservice.utils.exceptions.ApiUnprocessableEntity;
import com.zhopy.userservice.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping(value="/detail/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByUserId(@PathVariable("userId") String userId) throws ApiNotFound {
        this.userValidator.validatorById(userId);
        return ResponseEntity.ok(this.userService.findByUserId(userId));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody UserRequest userRequest) throws ApiUnprocessableEntity {
        this.userValidator.validator(userRequest);
        this.userService.save(userRequest);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> update(@PathVariable("userId")String userId, @RequestBody UserRequest userRequest) throws ApiNotFound {
        this.userValidator.validatorById(userId);
        this.userService.update(userRequest, userId);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<Object> delete(@PathVariable("userId")String userId) throws ApiNotFound {
        this.userValidator.validatorById(userId);
        this.userService.delete(userId);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
