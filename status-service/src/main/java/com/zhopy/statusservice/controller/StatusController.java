package com.zhopy.statusservice.controller;

import com.zhopy.statusservice.dto.StatusRequest;
import com.zhopy.statusservice.service.interfaces.IStatusService;
import com.zhopy.statusservice.utils.exeptions.ApiNotFound;
import com.zhopy.statusservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.statusservice.validator.IStatusValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "status-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    @Qualifier("StatusService")
    private IStatusService statusService;

    @Autowired
    @Qualifier("StatusValidator")
    private IStatusValidator statusValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.statusService.findAll());
    }

    @GetMapping(value = "/detail/{statusCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByStatusCode(@PathVariable("statusCode") Long statusCode) throws ApiNotFound {
        this.statusValidator.validatorById(statusCode);
        return ResponseEntity.ok(this.statusService.findByStatusCode(statusCode));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody StatusRequest statusRequest) throws ApiUnprocessableEntity {
        this.statusValidator.validator(statusRequest);
        this.statusService.save(statusRequest);
        return ResponseEntity.ok("The status was saved correctly");
    }

    @PutMapping("/update/{statusCode}")
    public ResponseEntity<Object> update(@PathVariable("statusCode") Long statusCode, @RequestBody StatusRequest statusRequest) throws ApiNotFound, ApiUnprocessableEntity {
        this.statusValidator.validatorByIdRequest(statusCode, statusRequest.getStatusCode());
        this.statusValidator.validatorUpdate(statusRequest);
        this.statusService.update(statusRequest, statusCode);
        return ResponseEntity.ok("The status was updated successfully");
    }

    @DeleteMapping("/delete/{statusCode}")
    public ResponseEntity<Object> delete(@PathVariable("statusCode") Long statusCode) throws ApiNotFound {
        this.statusValidator.validatorById(statusCode);
        this.statusService.delete(statusCode);
        return ResponseEntity.ok("The status was deleted successfully");
    }

    @GetMapping("/exist/{statusCode}")
    public ResponseEntity<Object> existsByStatusCode(@PathVariable("statusCode") Long statusCode) throws ApiNotFound {
        return ResponseEntity.ok(statusService.existsByStatusCode(statusCode));
    }

}
