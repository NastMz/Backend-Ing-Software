package com.zhopy.shoesservice.controller;

import com.zhopy.shoesservice.dto.ShoesDTO;
import com.zhopy.shoesservice.dto.ShoesRequest;
import com.zhopy.shoesservice.service.interfaces.IShoesService;
import com.zhopy.shoesservice.service.interfaces.IUploadFileService;
import com.zhopy.shoesservice.utils.exeptions.ApiNotFound;
import com.zhopy.shoesservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.shoesservice.validator.ShoesValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "shoes-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/shoes")
public class ShoesController {

    @Autowired
    private IShoesService shoeService;

    @Autowired
    private IUploadFileService uploadService;

    @Autowired
    private ShoesValidator shoesValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(this.shoeService.findAll());
    }

    @GetMapping(value = "/detail/{shoeCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByShoeCode(@PathVariable("shoeCode") String shoeCode) throws ApiNotFound {
        this.shoesValidator.validatorById(shoeCode);
        return ResponseEntity.ok(this.shoeService.findByShoeCode(shoeCode));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody ShoesRequest shoesRequest, @RequestParam("image") MultipartFile file) throws ApiUnprocessableEntity, IOException {
        this.shoesValidator.validator(shoesRequest);

        String imageName = this.uploadService.saveImage(file);
        shoesRequest.setImage(imageName);

        this.shoeService.save(shoesRequest);
        return ResponseEntity.ok("Los zapatos se guardaron correctamente");
    }

    @PutMapping("/update/{shoeCode}")
    public ResponseEntity<Object> update(@PathVariable("shoeCode") String shoeCode, @RequestBody ShoesRequest shoesRequest, @RequestParam("image") MultipartFile file) throws ApiNotFound, IOException, ApiUnprocessableEntity {
        this.shoesValidator.validatorByIdRequest(shoeCode, shoesRequest.getShoeCode());
        this.shoesValidator.validatorUpdate(shoesRequest);

        ShoesDTO shoe = this.shoeService.findByShoeCode(shoeCode);
        if (file.isEmpty()) {
            shoesRequest.setImage(shoe.getImage());
        } else {
            if (!shoe.getImage().equals("default.jpg")) {
                this.uploadService.deleteImage(shoe.getImage());
            }
            String imageName = this.uploadService.saveImage(file);
            shoesRequest.setImage(imageName);
        }

        this.shoeService.update(shoesRequest, shoeCode);
        return ResponseEntity.ok("Los zapatos se actualizaron correctamente");
    }

    @DeleteMapping("/delete/{shoeCode}")
    public ResponseEntity<Object> delete(@PathVariable("shoeCode") String shoeCode) throws ApiNotFound {
        this.shoesValidator.validatorById(shoeCode);

        ShoesDTO shoe = this.shoeService.findByShoeCode(shoeCode);
        if (!shoe.getImage().equals("default.jpg")) {
            this.uploadService.deleteImage(shoe.getImage());
        }

        this.shoeService.delete(shoeCode);
        return ResponseEntity.ok("Los zapatos se eliminaron correctamente");
    }

}
