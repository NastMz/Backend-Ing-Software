package com.zhopy.shoesservice.service.implementation;

import com.zhopy.shoesservice.repository.CategoryRepository;
import com.zhopy.shoesservice.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryImplement implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean existsByCategoryCode(Long categoryCode) {
        return categoryRepository.existsByCategoryCode(categoryCode);
    }

}
