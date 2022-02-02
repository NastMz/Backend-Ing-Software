package com.zhopy.userservice.controller;

import com.zhopy.userservice.entity.User;
import com.zhopy.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){ // ResponseEntity representa toda la respuesta HTTP: código de estado, encabezados y cuerpo
        List<User> users = userService.getAll();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){ // ResponseEntity representa toda la respuesta HTTP: código de estado, encabezados y cuerpo
        User user = userService.getUserById(userId);
        if (user == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<User> save(@RequestBody User user){ // ResponseEntity representa toda la respuesta HTTP: código de estado, encabezados y cuerpo
        User userNew = userService.save(user);
        return ResponseEntity.ok(userNew);
    }
}
