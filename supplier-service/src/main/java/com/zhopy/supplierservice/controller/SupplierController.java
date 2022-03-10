package com.zhopy.supplierservice.controller;

import com.zhopy.supplierservice.dto.SupplierRequest;
import com.zhopy.supplierservice.service.interfaces.ISupplierService;
import com.zhopy.supplierservice.utils.exeptions.ApiNotFound;
import com.zhopy.supplierservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.supplierservice.validator.ISupplierValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "supplier-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    @Qualifier("SupplierService")
    private ISupplierService supplierService;

    @Autowired
    @Qualifier("SupplierValidator")
    private ISupplierValidator supplierValidator;

    @CircuitBreaker(name = "cityCB", fallbackMethod = "fallBackFindAll")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.supplierService.findAll());
    }

    @CircuitBreaker(name = "cityCB", fallbackMethod = "fallBackFindBySupplierNit")
    @GetMapping(value = "/detail/{supplierNit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findBySupplierNit(@PathVariable("supplierNit") String supplierNit) throws ApiNotFound {
        this.supplierValidator.validatorById(supplierNit);
        return ResponseEntity.ok(this.supplierService.findBySupplierNit(supplierNit));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody SupplierRequest supplierRequest) throws ApiUnprocessableEntity {
        this.supplierValidator.validator(supplierRequest);
        this.supplierService.save(supplierRequest);
        return ResponseEntity.ok("The supplier was saved correctly");
    }

    @PutMapping("/update/{supplierNit}")
    public ResponseEntity<Object> update(@PathVariable("supplierNit") String supplierNit, @RequestBody SupplierRequest supplierRequest) throws ApiNotFound, ApiUnprocessableEntity {
        this.supplierValidator.validatorByIdRequest(supplierNit, supplierRequest.getSupplierNit());
        this.supplierValidator.validatorUpdate(supplierRequest);
        this.supplierService.update(supplierRequest, supplierNit);
        return ResponseEntity.ok("The supplier was updated successfully");
    }

    @DeleteMapping("/delete/{supplierNit}")
    public ResponseEntity<Object> delete(@PathVariable("supplierNit") String codeSupplier) throws ApiNotFound {
        this.supplierValidator.validatorById(codeSupplier);
        this.supplierService.delete(codeSupplier);
        return ResponseEntity.ok("The supplier was deleted correctly");
    }

    @GetMapping("/validate/{supplierNit}")
    public boolean existsBySupplierNit(@PathVariable("supplierNit") String supplierNit) {
        return supplierService.existsBySupplierNit(supplierNit);
    }

    private ResponseEntity<Object> fallBackFindAll(RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }

    private ResponseEntity<Object> fallBackFindBySupplierNit(@PathVariable("shoeCode") String shoeCode, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }

}
