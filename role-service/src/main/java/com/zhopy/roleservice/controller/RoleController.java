package com.zhopy.roleservice.controller;

import com.zhopy.roleservice.dto.RoleRequest;
import com.zhopy.roleservice.service.interfaces.IRoleService;
import com.zhopy.roleservice.utils.exeptions.ApiNotFound;
import com.zhopy.roleservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.roleservice.validator.IRoleValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "role-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    @Qualifier("RoleService")
    private IRoleService roleService;

    @Autowired
    @Qualifier("RoleValidator")
    private IRoleValidator roleValidator;

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
        return ResponseEntity.ok("The role was saved correctly");
    }

    @PutMapping("/update/{roleCode}")
    public ResponseEntity<Object> update(@PathVariable("roleCode") Long roleCode, @RequestBody RoleRequest roleRequest) throws ApiNotFound, ApiUnprocessableEntity {
        this.roleValidator.validatorByIdRequest(roleCode, roleRequest.getRoleCode());
        this.roleValidator.validatorUpdate(roleRequest);
        this.roleService.update(roleRequest, roleCode);
        return ResponseEntity.ok("The role was updated successfully");
    }

    @DeleteMapping("/delete/{roleCode}")
    public ResponseEntity<Object> delete(@PathVariable("roleCode") Long roleCode) throws ApiNotFound {
        this.roleValidator.validatorById(roleCode);
        this.roleService.delete(roleCode);
        return ResponseEntity.ok("The role was deleted successfully");
    }

    @GetMapping("/validate/{roleCode}")
    public ResponseEntity<Object> existsByRoleCode(@PathVariable("roleCode") Long roleCode) throws ApiNotFound {
        return ResponseEntity.ok(roleService.existsByRoleCode(roleCode));
    }

}
