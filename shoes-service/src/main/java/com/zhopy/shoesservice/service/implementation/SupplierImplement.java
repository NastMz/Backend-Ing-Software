package com.zhopy.shoesservice.service.implementation;

import com.zhopy.shoesservice.repository.SupplierRepository;
import com.zhopy.shoesservice.service.interfaces.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierImplement implements ISupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public boolean existsBySupplierNit(Long supplierNit) {
        return supplierRepository.existsBySupplierNit(supplierNit);
    }
}
