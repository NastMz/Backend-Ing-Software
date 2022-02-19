package com.zhopy.shoesservice.repository;

import com.zhopy.shoesservice.entity.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    @Transactional(readOnly = true)
    Optional<Supplier> findBySupplierName(String supplierName);

    boolean existsBySupplierName(String supplierName);

    boolean existsBySupplierNit(Long supplierNit);
}
