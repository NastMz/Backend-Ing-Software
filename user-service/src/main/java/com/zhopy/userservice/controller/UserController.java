package com.zhopy.userservice.controller;

import com.zhopy.userservice.dto.Message;
import com.zhopy.userservice.entity.User;
import com.zhopy.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAll(){
        List<User> userList = userService.getAll();
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    @GetMapping("/detail/{userId}")
    public ResponseEntity<User> getById(@PathVariable("userId") String userId){ // ResponseEntity representa toda la respuesta HTTP: código de estado, encabezados y cuerpo
        if(!userService.existsById(userId))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        User user = userService.getById(userId).get();
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody User user){
        if(userService.existsById(user.getUserId()))
            return new ResponseEntity(new Message("ese documento ya existe"), HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(user.getEmail()))
            return new ResponseEntity(new Message("ese email ya existe"), HttpStatus.BAD_REQUEST);

        userService.save(user);
        return new ResponseEntity(new Message("usuario creado"), HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> update(@PathVariable("userId")String userId, @RequestBody User user){
        if(!userService.existsById(userId))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        if(userService.existsByEmail(user.getEmail()) && !Objects.equals(userService.getById(user.getUserId()).get().getUserId(), userId))
            return new ResponseEntity(new Message("ese correo ya existe"), HttpStatus.BAD_REQUEST);

        userService.save(user);
        return new ResponseEntity(new Message("usuario actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable("userId")String userId){
        if(!userService.existsById(userId))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        userService.delete(userId);
        return new ResponseEntity(new Message("usuario eliminado"), HttpStatus.OK);
    }

}
