package com.zhopy.roleservice.controller;

import com.zhopy.roleservice.dto.Message;
import com.zhopy.roleservice.dto.RoleDto;
import com.zhopy.roleservice.entity.Role;
import com.zhopy.roleservice.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@CrossOrigin
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
    public ResponseEntity<?> save(@RequestBody RoleDto roleDto){
        if(StringUtils.isBlank(roleDto.getRoleName()))
            return new ResponseEntity(new Message("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(roleService.existsByName(roleDto.getRoleName()))
            return new ResponseEntity(new Message("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Role role = new Role(roleDto.getRoleName(), roleDto.isDeleted());
        roleService.save(role);
        return new ResponseEntity(new Message("rol creado"), HttpStatus.OK);
    }

    @PutMapping("/update/{roleCode}")
    public ResponseEntity<?> update(@PathVariable("roleCode")Long roleCode, @RequestBody RoleDto roleDto){
        if(!roleService.existsById(roleCode))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        if(roleService.existsByName(roleDto.getRoleName()) && roleService.getByName(roleDto.getRoleName()).get().getRoleCode() != roleCode)
            return new ResponseEntity(new Message("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(roleDto.getRoleName()))
            return new ResponseEntity(new Message("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
       
        Role role = roleService.getById(roleCode).get();
        role.setRoleName(roleDto.getRoleName());
        roleService.save(role);
        return new ResponseEntity(new Message("role actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{roleCode}")
    public ResponseEntity<?> delete(@PathVariable("roleCode")Long codeRole){
        if(!roleService.existsById(codeRole))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        roleService.delete(codeRole);
        return new ResponseEntity(new Message("role eliminado"), HttpStatus.OK);
    }

}
