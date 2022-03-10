package com.zhopy.orderservice.controller;

import com.zhopy.orderservice.dto.OrderRequest;
import com.zhopy.orderservice.service.interfaces.IOrderService;
import com.zhopy.orderservice.utils.exeptions.ApiNotFound;
import com.zhopy.orderservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.orderservice.validator.IOrderValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "order-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    @Qualifier("OrderService")
    private IOrderService orderService;

    @Autowired
    @Qualifier("OrderValidator")
    private IOrderValidator orderValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.orderService.findAll());
    }


    @GetMapping(value = "/detail/{orderNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByOrderNumber(@PathVariable("orderNumber") Long orderNumber) throws ApiNotFound {
        this.orderValidator.validatorById(orderNumber);
        return ResponseEntity.ok(this.orderService.findByOrderNumber(orderNumber));
    }

    @CircuitBreaker(name = "user_status_buyCB", fallbackMethod = "fallBackSave")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody OrderRequest orderRequest) throws ApiUnprocessableEntity {
        this.orderValidator.validator(orderRequest);
        this.orderService.save(orderRequest);
        return ResponseEntity.ok("The order was saved successfully");
    }

    @CircuitBreaker(name = "user_status_buyCB", fallbackMethod = "fallBackUpdate")
    @PutMapping("/update/{orderNumber}")
    public ResponseEntity<Object> update(@PathVariable("orderNumber") Long orderNumber, @RequestBody OrderRequest orderRequest) throws ApiNotFound, ApiUnprocessableEntity {
        this.orderValidator.validatorByIdRequest(orderNumber, orderRequest.getOrderNumber());
        this.orderValidator.validatorUpdate(orderRequest);
        this.orderService.update(orderRequest, orderNumber);
        return ResponseEntity.ok("The order was successfully updated");
    }

    @DeleteMapping("/delete/{orderNumber}")
    public ResponseEntity<Object> delete(@PathVariable("orderNumber") Long orderNumber) throws ApiNotFound {
        this.orderValidator.validatorById(orderNumber);
        this.orderService.delete(orderNumber);
        return ResponseEntity.ok("The order was successfully deleted");
    }

    private ResponseEntity<Object> fallBackSave(@RequestBody OrderRequest orderRequest, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }

    private ResponseEntity<Object> fallBackSave(@PathVariable("orderNumber") Long orderNumber, @RequestBody OrderRequest orderRequest, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }
}
