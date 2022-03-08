package com.zhopy.shoesservice.service.implementation;

import com.zhopy.shoesservice.dto.ShoesDTO;
import com.zhopy.shoesservice.dto.ShoesRequest;
import com.zhopy.shoesservice.entity.Shoes;
import com.zhopy.shoesservice.feignclients.CategoryFeignClient;
import com.zhopy.shoesservice.feignclients.SupplierFeignClient;
import com.zhopy.shoesservice.model.Category;
import com.zhopy.shoesservice.model.Supplier;
import com.zhopy.shoesservice.repository.ShoesRepository;
import com.zhopy.shoesservice.service.interfaces.IShoesService;
import com.zhopy.shoesservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("ShoesService")
public class ShoesImplement implements IShoesService {

    @Autowired
    private ShoesRepository shoesRepository;

    @Autowired
    private CategoryFeignClient categoryFeignClient;

    @Autowired
    private SupplierFeignClient supplierFeignClient;

    @Override
    public List<ShoesDTO> findAll() {
        List<ShoesDTO> dto = new ArrayList<>();
        Iterable<Shoes> shoes = this.shoesRepository.findAll();

        for (Shoes shoe : shoes) {
            ShoesDTO shoesDTO = MapperHelper.modelMapper().map(shoe, ShoesDTO.class);
            shoesDTO.setCategoryName(findByCategoryCode(shoe.getCategoryCode()).getCategoryName());
            shoesDTO.setSupplierName(findBySupplierNit(shoe.getSupplierNit()).getSupplierName());
            dto.add(shoesDTO);
        }

        return dto;
    }

    @Override
    public ShoesDTO findByShoeCode(String shoeCode) {
        Optional<Shoes> shoe = this.shoesRepository.findById(shoeCode);
        if (!shoe.isPresent()) {
            return null;
        }
        ShoesDTO shoesDTO = MapperHelper.modelMapper().map(shoe.get(), ShoesDTO.class);
        shoesDTO.setCategoryName(findByCategoryCode(shoe.get().getCategoryCode()).getCategoryName());
        shoesDTO.setSupplierName(findBySupplierNit(shoe.get().getSupplierNit()).getSupplierName());
        return shoesDTO;
    }

    @Override
    public ShoesDTO findByShoeName(String shoeName) {
        Optional<Shoes> shoe = this.shoesRepository.findByShoeName(shoeName);
        if (shoe.isPresent()) {
            return null;
        }

        return MapperHelper.modelMapper().map(shoe, ShoesDTO.class);
    }

    @Override
    public void save(ShoesRequest shoesRequest) {
        Shoes shoe = MapperHelper.modelMapper().map(shoesRequest, Shoes.class);
        this.shoesRepository.save(shoe);
    }

    @Override
    public void update(ShoesRequest shoesRequest, String shoeCode) {
        Shoes shoe = MapperHelper.modelMapper().map(shoesRequest, Shoes.class);
        this.shoesRepository.save(shoe);
    }

    @Override
    public void delete(String shoeCode) {
        shoesRepository.deleteById(shoeCode);
    }

    @Override
    public boolean existsByShoeCode(String shoeCode) {
        return shoesRepository.existsByShoeCode(shoeCode);
    }

    @Override
    public boolean existsByShoeName(String shoeName) {
        return shoesRepository.existsByShoeName(shoeName);
    }

    @Override
    public Category findByCategoryCode(Long categoryCode) {
        Category category;
        try {
            category = categoryFeignClient.findByCategoryCode(categoryCode);
        } catch (Exception e) {
            return null;
        }
        return category;
    }

    @Override
    public boolean existsByCategoryCode(Long categoryCode) {
        boolean exists;
        try {
            exists = categoryFeignClient.existsByCategoryCode(categoryCode);
        } catch (Exception e) {
            return false;
        }
        return exists;
    }

    @Override
    public Supplier findBySupplierNit(String supplierNit) {
        Supplier supplier;
        try {
            supplier = supplierFeignClient.findBySupplierNit(supplierNit);
        } catch (Exception e) {
            return null;
        }
        return supplier;
    }

    @Override
    public boolean existsBySupplierNit(String supplierNit) {
        boolean exists;
        try {
            exists = supplierFeignClient.existsBySupplierNit(supplierNit);
        } catch (Exception e) {
            return false;
        }
        return exists;
    }

}
