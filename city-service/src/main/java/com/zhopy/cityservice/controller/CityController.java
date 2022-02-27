package com.zhopy.cityservice.controller;

import com.zhopy.cityservice.dto.CityRequest;
import com.zhopy.cityservice.service.interfaces.ICityService;
import com.zhopy.cityservice.utils.exeptions.ApiNotFound;
import com.zhopy.cityservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.cityservice.validator.ICityValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "city-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/city")
public class CityController {

    @Autowired
    @Qualifier("CityService")
    private ICityService cityService;


    @Autowired
    @Qualifier("CityValidator")
    private ICityValidator cityValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.cityService.findAll());
    }

    @GetMapping(value = "/detail/{cityCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByCityCode(@PathVariable("cityCode") Long cityCode) throws ApiNotFound {
        this.cityValidator.validatorById(cityCode);
        return ResponseEntity.ok(this.cityService.findByCityCode(cityCode));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody CityRequest cityRequest) throws ApiUnprocessableEntity {
        this.cityValidator.validator(cityRequest);
        this.cityService.save(cityRequest);
        return ResponseEntity.ok("La ciudad se guardo correctamente");
    }

    @PutMapping("/update/{cityCode}")
    public ResponseEntity<Object> update(@PathVariable("cityCode") Long cityCode, @RequestBody CityRequest cityRequest) throws ApiNotFound, ApiUnprocessableEntity {
        this.cityValidator.validatorByIdRequest(cityCode, cityRequest.getCityCode());
        this.cityValidator.validatorUpdate(cityRequest);
        this.cityService.update(cityRequest, cityCode);
        return ResponseEntity.ok("La ciudad se actualizo correctamente");
    }

    @DeleteMapping("/delete/{cityCode}")
    public ResponseEntity<Object> delete(@PathVariable("cityCode") Long codeRole) throws ApiNotFound {
        this.cityValidator.validatorById(codeRole);
        this.cityService.delete(codeRole);
        return ResponseEntity.ok("La ciudad se borro correctamente");
    }

}
