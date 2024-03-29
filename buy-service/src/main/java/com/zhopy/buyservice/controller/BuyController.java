package com.zhopy.buyservice.controller;

import com.zhopy.buyservice.dto.BuyRequest;
import com.zhopy.buyservice.service.interfaces.IBuyService;
import com.zhopy.buyservice.utils.exeptions.ApiNotFound;
import com.zhopy.buyservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.buyservice.validator.IBuyValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "buy-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/buy")
public class BuyController {

    @Autowired
    @Qualifier("BuyService")
    private IBuyService buyService;

    @Autowired
    @Qualifier("BuyValidator")
    private IBuyValidator buyValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.buyService.findAll());
    }


    @GetMapping(value = "/detail/{buyNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByBuyNumber(@PathVariable("buyNumber") Long buyNumber) throws ApiNotFound {
        this.buyValidator.validatorById(buyNumber);
        return ResponseEntity.ok(this.buyService.findByBuyNumber(buyNumber));
    }

    @CircuitBreaker(name = "user_shoesCB", fallbackMethod = "fallBackSave")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody BuyRequest buyRequest) throws ApiUnprocessableEntity {
        this.buyValidator.validator(buyRequest);
        Long buyNumber = this.buyService.save(buyRequest);
        this.buyService.saveShoes(buyRequest.getShoesList(), buyNumber);
        return ResponseEntity.ok("The purchase was saved successfully");
    }

    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackUpdate")
    @PutMapping("/update/{buyNumber}")
    public ResponseEntity<Object> update(@PathVariable("buyNumber") Long buyNumber, @RequestBody BuyRequest buyRequest) throws ApiNotFound, ApiUnprocessableEntity {
        this.buyValidator.validatorByIdRequest(buyNumber, buyRequest.getBuyNumber());
        this.buyValidator.validatorUpdate(buyRequest);
        this.buyService.update(buyRequest, buyNumber);
        return ResponseEntity.ok("The purchase was successfully updated");
    }

    @DeleteMapping("/delete/{buyNumber}")
    public ResponseEntity<Object> delete(@PathVariable("buyNumber") Long buyNumber) throws ApiNotFound {
        this.buyValidator.validatorById(buyNumber);
        this.buyService.delete(buyNumber);
        return ResponseEntity.ok("The purchase was successfully deleted");
    }

    @PostMapping("/exists/{buyNumber}")
    public ResponseEntity<Object> existsByBuyNumber(@PathVariable("buyNumber") Long buyNumber) throws ApiNotFound {
        return ResponseEntity.ok(buyService.existsByBuyNumber(buyNumber));
    }

    private ResponseEntity<Object> fallBackSave(@RequestBody BuyRequest buyRequest, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }

    private ResponseEntity<Object> fallBackSave(@PathVariable("buyNumber") Long buyNumber, @RequestBody BuyRequest buyRequest, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }
}
