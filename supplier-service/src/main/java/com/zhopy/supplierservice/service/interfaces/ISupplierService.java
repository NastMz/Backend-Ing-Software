package com.zhopy.supplierservice.service.interfaces;

import com.zhopy.supplierservice.dto.SupplierDTO;
import com.zhopy.supplierservice.dto.SupplierRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISupplierService {
    List<SupplierDTO> findAll();

    SupplierDTO findBySupplierNit(String supplierNit);

    SupplierDTO findBySupplierName(String supplierName);

    void save(SupplierRequest supplierRequest);

    void update(SupplierRequest supplierRequest, String supplierNit);

    void delete(String supplierNit);

    boolean existsBySupplierNit(String supplierNit);

    boolean existsBySupplierName(String supplierName);

    boolean existsByCityCode(Long cityCode);
}
