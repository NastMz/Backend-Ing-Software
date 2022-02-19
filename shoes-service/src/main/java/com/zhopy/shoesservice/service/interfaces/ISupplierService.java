package com.zhopy.shoesservice.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface ISupplierService {
    boolean existsBySupplierNit(Long supplierNit);
}
