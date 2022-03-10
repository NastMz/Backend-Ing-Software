package com.zhopy.shoesboughtservice.controller;

import com.zhopy.shoesboughtservice.dto.ShoesBoughtRequests;
import com.zhopy.shoesboughtservice.service.interfaces.IShoesBoughtService;
import com.zhopy.shoesboughtservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.shoesboughtservice.validator.IShoesBoughtValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "shoesbought-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/shoesbought")
public class ShoesBoughtController {

    @Autowired
    @Qualifier("ShoesBoughtService")
    private IShoesBoughtService shoesBoughtService;

    @Autowired
    @Qualifier("ShoesBoughtValidator")
    private IShoesBoughtValidator shoesBoughtValidator;

    @GetMapping(value = "/listByShoe/{shoeCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllByShoeCode(@PathVariable("shoeCode") String shoeCode) {
        return ResponseEntity.ok(this.shoesBoughtService.findByShoeCode(shoeCode));
    }

    @GetMapping(value = "/listByBuy/{buyNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllByBuyNumber(@PathVariable("buyNumber") Long buyNumber) {
        return ResponseEntity.ok(this.shoesBoughtService.findByBuyNumber(buyNumber));
    }


    @CircuitBreaker(name = " shoe_buyCB", fallbackMethod = "fallBackSave")
    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@RequestBody ShoesBoughtRequests shoesBoughtRequest) throws ApiUnprocessableEntity {
        this.shoesBoughtValidator.validator(shoesBoughtRequest);
        this.shoesBoughtService.save(shoesBoughtRequest);
        return ResponseEntity.ok("The shoes list was saved correctly");
    }

    private ResponseEntity<Object> fallBackSave(@RequestBody ShoesBoughtRequests shoesBoughtRequest, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }

}
