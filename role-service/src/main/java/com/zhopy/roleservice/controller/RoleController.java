package com.zhopy.roleservice.controller;

import com.zhopy.roleservice.dto.Message;
import com.zhopy.roleservice.entity.Role;
import com.zhopy.roleservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<List<Role>> getAll(){
        List<Role> roleList = roleService.getAll();
        return new ResponseEntity(roleList, HttpStatus.OK);
    }

    @GetMapping("/detail/{roleCode}")
    public ResponseEntity<Role> getById(@PathVariable("roleCode") Long roleCode){ // ResponseEntity representa toda la respuesta HTTP: código de estado, encabezados y cuerpo
        if(!roleService.existsById(roleCode))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        Role role = roleService.getById(roleCode).get();
        return new ResponseEntity(role, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Role role){
        if(roleService.existsByName(role.getRoleName()))
            return new ResponseEntity(new Message("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        roleService.save(role);
        return new ResponseEntity(new Message("rol creado"), HttpStatus.OK);
    }

    @PutMapping("/update/{roleCode}")
    public ResponseEntity<?> update(@PathVariable("roleCode")Long roleCode, @RequestBody Role role){
        if(!roleService.existsById(roleCode))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        if(roleService.existsByName(role.getRoleName()) && roleService.getByName(role.getRoleName()).get().getRoleCode() != roleCode)
            return new ResponseEntity(new Message("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        roleService.save(role);
        return new ResponseEntity(new Message("rol actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{roleCode}")
    public ResponseEntity<?> delete(@PathVariable("roleCode")Long codeRole){
        if(!roleService.existsById(codeRole))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        roleService.delete(codeRole);
        return new ResponseEntity(new Message("rol eliminado"), HttpStatus.OK);
    }

}
