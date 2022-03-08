package com.zhopy.supplierservice.repository;

import com.zhopy.supplierservice.entity.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, String> {
    @Transactional(readOnly = true)
    Optional<Supplier> findBySupplierName(String supplierName);

    boolean existsBySupplierName(String supplierName);

    boolean existsBySupplierNit(String supplierNit);
}
