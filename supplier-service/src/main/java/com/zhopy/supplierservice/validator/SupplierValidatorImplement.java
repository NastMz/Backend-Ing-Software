package com.zhopy.supplierservice.validator;

import com.zhopy.supplierservice.dto.SupplierDTO;
import com.zhopy.supplierservice.dto.SupplierRequest;
import com.zhopy.supplierservice.service.interfaces.ISupplierService;
import com.zhopy.supplierservice.utils.exeptions.ApiNotFound;
import com.zhopy.supplierservice.utils.exeptions.ApiUnprocessableEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("SupplierValidator")
public class SupplierValidatorImplement implements ISupplierValidator {

    @Autowired
    @Qualifier("SupplierService")
    private ISupplierService supplierService;

    @Override
    public void validator(SupplierRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        if (supplierService.existsBySupplierName(request.getSupplierName())) {
            this.message422("The name already exists");
        }
    }

    @Override
    public void validatorUpdate(SupplierRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        validateName(request);
    }

    @Override
    public void validatorById(String id) throws ApiNotFound {
        if (!supplierService.existsBySupplierNit(id)) {
            this.message404("The nit does not exist");
        }
    }

    @Override
    public void validatorByIdRequest(String urlNit, String roleNit) throws ApiNotFound, ApiUnprocessableEntity {
        if (!supplierService.existsBySupplierNit(urlNit)) {
            this.message404("The nit does not exist");
        }
        if (!urlNit.equals(roleNit)) {
            this.message422("The request body nit does not match the uri nit");
        }
    }

    private void validateData(SupplierRequest request) throws ApiUnprocessableEntity {
        if (request.getSupplierNit() == null || StringUtils.isBlank(request.getSupplierNit())) {
            this.message422("The nit cannot be empty");
        }
        if (request.getSupplierName() == null || StringUtils.isBlank(request.getSupplierName())) {
            this.message422("The name cannot be empty");
        }
        if (request.getCityCode() == null || StringUtils.isBlank(request.getCityCode().toString())) {
            this.message422("The city code cannot be empty");
        }
        if (!supplierService.existsByCityCode(request.getCityCode())) {
            this.message422("The city code does not exist");
        }
    }

    private void validateName(SupplierRequest roleRequest) throws ApiUnprocessableEntity {
        SupplierDTO supplierSearch = this.supplierService.findBySupplierNit(roleRequest.getSupplierNit());
        if (!roleRequest.getSupplierName().equals(supplierSearch.getSupplierName()) && supplierService.existsBySupplierName(roleRequest.getSupplierName())) {
            this.message422("The name already exists");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
