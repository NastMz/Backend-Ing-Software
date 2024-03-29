package com.zhopy.shoesservice.controller;

import com.zhopy.shoesservice.dto.ShoesDTO;
import com.zhopy.shoesservice.dto.ShoesRequest;
import com.zhopy.shoesservice.service.interfaces.IShoesService;
import com.zhopy.shoesservice.service.interfaces.IUploadFileService;
import com.zhopy.shoesservice.utils.exeptions.ApiNotFound;
import com.zhopy.shoesservice.utils.exeptions.ApiUnprocessableEntity;
import com.zhopy.shoesservice.validator.IShoesValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@OpenAPIDefinition(info = @Info(title = "Zhopy", description = "shoes-service controller", version = "v1", license = @License(name = "", url = "")))
@RestController
@RequestMapping("/api/shoes")
@CrossOrigin(origins = "*")
public class ShoesController {

    @Autowired
    @Qualifier("ShoesService")
    private IShoesService shoeService;

    @Autowired
    @Qualifier("UploadService")
    private IUploadFileService uploadService;

    @Autowired
    @Qualifier("ShoesValidator")
    private IShoesValidator shoesValidator;

    @CircuitBreaker(name = "category_supplierCB", fallbackMethod = "fallBackFindAll")
    @GetMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> findAll() {
        List<ShoesDTO> shoes = this.shoeService.findAll();
        return ResponseEntity.ok(shoes);
    }

    @CircuitBreaker(name = "category_supplierCB", fallbackMethod = "fallBackFindByShoeCode")
    @GetMapping(value = "/detail/{shoeCode}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> findByShoeCode(@PathVariable("shoeCode") String shoeCode) throws ApiNotFound, IOException {
        this.shoesValidator.validatorById(shoeCode);
        ShoesDTO shoe = this.shoeService.findByShoeCode(shoeCode);
        return ResponseEntity.ok(shoe);
    }

    @CircuitBreaker(name = "category_supplierCB", fallbackMethod = "fallBackSave")
    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> save(@RequestPart ShoesRequest shoesRequest, @RequestPart(required = false) MultipartFile image) throws ApiUnprocessableEntity, IOException {
        this.shoesValidator.validator(shoesRequest);

        String imageName = this.uploadService.getImageName(image);
        byte[] imageBytes = this.uploadService.getImageBytes(image);
        shoesRequest.setImageName(imageName);
        shoesRequest.setImageBytes(imageBytes);

        this.shoeService.save(shoesRequest);
        return ResponseEntity.ok("The shoes were stored correctly");
    }

    @PutMapping(value = "/update/{shoeCode}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> update(@PathVariable("shoeCode") String shoeCode, @RequestPart ShoesRequest shoesRequest, @RequestPart(required = false) MultipartFile image) throws ApiNotFound, IOException, ApiUnprocessableEntity {
        this.shoesValidator.validatorByIdRequest(shoeCode, shoesRequest.getShoeCode());
        this.shoesValidator.validatorUpdate(shoesRequest);

        ShoesDTO shoe = this.shoeService.findByShoeCode(shoeCode);
        if (image.isEmpty()) {
            shoesRequest.setImageName(shoe.getImageName());
            shoesRequest.setImageBytes(shoe.getImageBytes());
        } else {
            String imageName = this.uploadService.getImageName(image);
            byte[] imageBytes = this.uploadService.getImageBytes(image);
            shoesRequest.setImageName(imageName);
            shoesRequest.setImageBytes(imageBytes);
        }

        this.shoeService.update(shoesRequest, shoeCode);
        return ResponseEntity.ok("Shoes updated successfully");
    }

    @DeleteMapping("/delete/{shoeCode}")
    public ResponseEntity<Object> delete(@PathVariable("shoeCode") String shoeCode) throws ApiNotFound {
        this.shoesValidator.validatorById(shoeCode);
        this.shoeService.delete(shoeCode);
        return ResponseEntity.ok("The shoes were removed successfully");
    }

    @GetMapping(value = "/getImage/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable String name) throws IOException {
        String route = "shoes-service//images//";
        File file = new File(route + name);
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }

    @PostMapping("/exists/{shoeCode}")
    public ResponseEntity<Object> existsByShoeCode(@PathVariable("shoeCode") String shoeCode) throws ApiNotFound {
        return ResponseEntity.ok(shoeService.existsByShoeCode(shoeCode));
    }

    private ResponseEntity<Object> fallBackFindAll(RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }

    private ResponseEntity<Object> fallBackFindByShoeCode(@PathVariable("shoeCode") String shoeCode, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }

    private ResponseEntity<Object> fallBackSave(@RequestPart ShoesRequest shoesRequest, @RequestPart(required = false) MultipartFile image, RuntimeException e) {
        return ResponseEntity.ok("The request was not possible, sorry for the inconvenience. We are working to fix the problem\n" + e);
    }

}
