package com.zhopy.cityservice.controller;

import com.zhopy.cityservice.dto.CityRequest;
import com.zhopy.cityservice.service.interfaces.ICityService;
import com.zhopy.cityservice.utils.exeptions.ApiNotFound;
import com.zhopy.cityservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.cityservice.validator.CityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/city")
public class CityController {

    @Autowired
    private ICityService cityService;

    @Autowired
    private CityValidator cityValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.cityService.findAll());
    }

    @GetMapping(value = "/detail/{cityCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByRoleCode(@PathVariable("cityCode") Long cityCode) throws ApiNotFound {
        this.cityValidator.validatorById(cityCode);
        return ResponseEntity.ok(this.cityService.findByCityCode(cityCode));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody CityRequest cityRequest) throws ApiUnprocessableEntity {
        this.cityValidator.validator(cityRequest);
        this.cityService.save(cityRequest);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PutMapping("/update/{cityCode}")
    public ResponseEntity<Object> update(@PathVariable("cityCode") Long cityCode, @RequestBody CityRequest cityRequest) throws ApiNotFound {
        this.cityValidator.validatorById(cityCode);
        this.cityService.update(cityRequest, cityCode);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @DeleteMapping("/delete/{cityCode}")
    public ResponseEntity<Object> delete(@PathVariable("cityCode") Long codeRole) throws ApiNotFound {
        this.cityValidator.validatorById(codeRole);
        this.cityService.delete(codeRole);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
