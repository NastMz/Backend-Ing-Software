package com.zhopy.shoesservice.validator;

import com.zhopy.shoesservice.dto.ShoesDTO;
import com.zhopy.shoesservice.dto.ShoesRequest;
import com.zhopy.shoesservice.service.interfaces.ICategoryService;
import com.zhopy.shoesservice.service.interfaces.IShoesService;
import com.zhopy.shoesservice.service.interfaces.ISupplierService;
import com.zhopy.shoesservice.utils.exeptions.ApiNotFound;
import com.zhopy.shoesservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoesValidatorImplement implements ShoesValidator {

    @Autowired
    private IShoesService shoeService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ISupplierService supplierService;

    @Override
    public void validator(ShoesRequest request) throws ApiUnprocessableEntity {
        if (request.getShoeCode() == null || request.getShoeCode().isBlank()) {
            this.message422("El codigo no puede estar vacio");
        }
        validateData(request);
        if (shoeService.existsByShoeCode(request.getShoeCode())) {
            this.message422("El codigo ya existe");
        }
        if (shoeService.existsByShoeName(request.getShoeName())) {
            this.message422("El nombre ya existe");
        }
    }

    @Override
    public void validatorUpdate(ShoesRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        validateName(request);
    }

    @Override
    public void validatorById(String id) throws ApiNotFound {
        if (!shoeService.existsByShoeCode(id)) {
            this.message404("El codigo no existe");
        }
    }

    @Override
    public void validatorByIdRequest(String urlCode, String shoeCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!shoeService.existsByShoeCode(urlCode)) {
            this.message404("El codigo no existe");
        }
        if (!urlCode.equals(shoeCode)) {
            this.message422("El codigo del cuerpo de la peticion no coincide con el de la uri");
        }
    }

    private void validateData(ShoesRequest request) throws ApiUnprocessableEntity {
        if (request.getShoeName() == null || request.getShoeName().isBlank()) {
            this.message422("El nombre no puede estar vacio");
        }
        if (request.getPrice() == null || request.getPrice().isBlank()) {
            this.message422("El precio no puede estar vacio");
        }
        if (request.getStock() == null || request.getStock().isBlank()) {
            this.message422("El stock no puede estar vacio");
        }
        if (request.getDescription() == null || request.getDescription().isBlank()) {
            this.message422("La descripcion no puede estar vacia");
        }
        if (!categoryService.existsByCategoryCode(request.getCategoryCode())) {
            this.message422("El codigo de categoria no existe");
        }
        if (!supplierService.existsBySupplierNit(request.getSupplierNit())) {
            this.message422("El codigo de proveedor no existe");
        }
    }

    private void validateName(ShoesRequest shoeRequest) throws ApiUnprocessableEntity {
        ShoesDTO shoeSearch = this.shoeService.findByShoeCode(shoeRequest.getShoeCode());
        if (!shoeRequest.getShoeName().equals(shoeSearch.getShoeName()) && shoeService.existsByShoeName(shoeRequest.getShoeName())) {
            this.message422("El nombre ya existe");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
