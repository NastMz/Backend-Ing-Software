package com.zhopy.supplierservice.service.implementation;

import com.zhopy.supplierservice.dto.SupplierDTO;
import com.zhopy.supplierservice.dto.SupplierRequest;
import com.zhopy.supplierservice.entity.Supplier;
import com.zhopy.supplierservice.feignclients.CityFeignClient;
import com.zhopy.supplierservice.repository.SupplierRepository;
import com.zhopy.supplierservice.service.interfaces.ISupplierService;
import com.zhopy.supplierservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("SupplierService")
public class SupplierImplement implements ISupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CityFeignClient cityFeignClient;

    @Override
    public List<SupplierDTO> findAll() {
        List<SupplierDTO> dto = new ArrayList<>();
        Iterable<Supplier> categories = this.supplierRepository.findAll();

        for (Supplier supplier : categories) {
            SupplierDTO supplierDTO = MapperHelper.modelMapper().map(supplier, SupplierDTO.class);
            supplierDTO.setCityName(cityFeignClient.findByCityCode(supplier.getCityCode()).getCityName());
            dto.add(supplierDTO);
        }

        return dto;
    }

    @Override
    public SupplierDTO findBySupplierNit(String supplierNit) {
        Optional<Supplier> supplier = this.supplierRepository.findById(supplierNit);
        if (!supplier.isPresent()) {
            return null;
        }
        SupplierDTO supplierDTO = MapperHelper.modelMapper().map(supplier.get(), SupplierDTO.class);
        supplierDTO.setCityName(cityFeignClient.findByCityCode(supplier.get().getCityCode()).getCityName());
        return supplierDTO;
    }

    @Override
    public SupplierDTO findBySupplierName(String supplierName) {
        Optional<Supplier> supplier = this.supplierRepository.findBySupplierName(supplierName);
        if (supplier.isPresent()) {
            return null;
        }
        SupplierDTO supplierDTO = MapperHelper.modelMapper().map(supplier.get(), SupplierDTO.class);
        supplierDTO.setSupplierNit(cityFeignClient.findByCityCode(supplier.get().getCityCode()).getCityName());
        return supplierDTO;
    }

    @Override
    public void save(SupplierRequest supplierRequest) {
        Supplier supplier = MapperHelper.modelMapper().map(supplierRequest, Supplier.class);
        this.supplierRepository.save(supplier);
    }

    @Override
    public void update(SupplierRequest supplierRequest, String supplierNit) {
        Supplier supplier = MapperHelper.modelMapper().map(supplierRequest, Supplier.class);
        this.supplierRepository.save(supplier);
    }

    @Override
    public void delete(String supplierNit) {
        supplierRepository.deleteById(supplierNit);
    }

    @Override
    public boolean existsBySupplierNit(String supplierNit) {
        return supplierRepository.existsBySupplierNit(supplierNit);
    }

    @Override
    public boolean existsBySupplierName(String supplierName) {
        return supplierRepository.existsBySupplierName(supplierName);
    }

    @Override
    public boolean existsByCityCode(Long cityCode) {
        return cityFeignClient.existsByCityCode(cityCode);
    }


}
