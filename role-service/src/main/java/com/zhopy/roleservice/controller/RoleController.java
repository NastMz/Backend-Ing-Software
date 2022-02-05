package com.zhopy.roleservice.controller;

import com.zhopy.roleservice.dto.RoleRequest;
import com.zhopy.roleservice.service.interfaces.IRoleService;
import com.zhopy.roleservice.utils.exeptions.ApiNotFound;
import com.zhopy.roleservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.roleservice.validator.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private RoleValidator roleValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.roleService.findAll());
    }

    @GetMapping(value = "/detail/{roleCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByRoleCode(@PathVariable("roleCode") Long roleCode) throws ApiNotFound {
        this.roleValidator.validatorById(roleCode);
        return ResponseEntity.ok(this.roleService.findByRoleCode(roleCode));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody RoleRequest roleRequest) throws ApiUnprocessableEntity {
        this.roleValidator.validator(roleRequest);
        this.roleService.save(roleRequest);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("/update/{roleCode}")
    public ResponseEntity<Object> update(@PathVariable("roleCode") Long roleCode, @RequestBody RoleRequest roleRequest) throws ApiNotFound {
        this.roleValidator.validatorById(roleCode);
        this.roleService.update(roleRequest, roleCode);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @DeleteMapping("/delete/{roleCode}")
    public ResponseEntity<Object> delete(@PathVariable("roleCode") Long codeRole) throws ApiNotFound {
        this.roleValidator.validatorById(codeRole);
        this.roleService.delete(codeRole);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
