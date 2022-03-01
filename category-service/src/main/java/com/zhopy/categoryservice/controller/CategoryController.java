package com.zhopy.categoryservice.controller;

import com.zhopy.categoryservice.dto.CategoryRequest;
import com.zhopy.categoryservice.service.interfaces.ICategoryService;
import com.zhopy.categoryservice.utils.exeptions.ApiNotFound;
import com.zhopy.categoryservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.categoryservice.validator.ICategoryValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "category-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/category")
public class CategoryController{
    @Autowired
    @Qualifier("CategoryService")
    private ICategoryService categoryService;

    @Autowired
    @Qualifier("CategoryValidator")
    private ICategoryValidator categoryValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.categoryService.findAll());
    }

    @GetMapping(value = "/detail/{categoryCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByCategoryCode(@PathVariable("categoryCode") Long categoryCode) throws ApiNotFound {
        this.categoryValidator.validatorById(categoryCode);
        return ResponseEntity.ok(this.categoryService.findByCategoryCode(categoryCode));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody CategoryRequest categoryRequest) throws ApiUnprocessableEntity {
        this.categoryValidator.validator(categoryRequest);
        this.categoryService.save(categoryRequest);
        return ResponseEntity.ok("The category was saved correctly");
    }

    @PutMapping("/update/{categoryCode}")
    public ResponseEntity<Object> update(@PathVariable("categoryCode") Long categoryCode, @RequestBody CategoryRequest categoryRequest) throws ApiNotFound, ApiUnprocessableEntity {
        this.categoryValidator.validatorByIdRequest(categoryCode, categoryRequest.getCategoryCode());
        this.categoryValidator.validatorUpdate(categoryRequest);
        this.categoryService.update(categoryRequest, categoryCode);
        return ResponseEntity.ok("The category was updated successfully");
    }

    @DeleteMapping("/delete/{categoryCode}")
    public ResponseEntity<Object> delete(@PathVariable("categoryCode") Long codeCategory) throws ApiNotFound {
        this.categoryValidator.validatorById(codeCategory);
        this.categoryService.delete(codeCategory);
        return ResponseEntity.ok("The category was deleted correctly");
    }

    @GetMapping("/validate/{categoryCode}")
    public boolean existsByCategoryCode(@PathVariable("categoryCode") Long categoryCode) {
        return categoryService.existsByCategoryCode(categoryCode);
    }

}
