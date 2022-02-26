package com.zhopy.categoryservice.service.implementation;

import com.zhopy.categoryservice.dto.CategoryDTO;
import com.zhopy.categoryservice.dto.CategoryRequest;
import com.zhopy.categoryservice.entity.Category;
import com.zhopy.categoryservice.repository.CategoryRepository;
import com.zhopy.categoryservice.service.interfaces.ICategoryService;
import com.zhopy.categoryservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("CategoryService")
public class CategoryImplement implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> findAll()
    {
        List<CategoryDTO> dto = new ArrayList<>();
        Iterable<Category> categories = this.categoryRepository.findAll();

        for (Category category : categories) {
            CategoryDTO categoryDTO = MapperHelper.modelMapper().map(category, CategoryDTO.class);
            dto.add(categoryDTO);
        }

        return dto;
    }

    @Override
    public CategoryDTO findByCategoryCode(Long categoryCode) {
        Optional<Category> category = this.categoryRepository.findById(categoryCode);
        if (!category.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(category.get(), CategoryDTO.class);
    }

    @Override
    public CategoryDTO findByCategoryName(String categoryName) {
        Optional<Category> category = this.categoryRepository.findByCategoryName(categoryName);
        if (category.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(category, CategoryDTO.class);
    }

    @Override
    public void save(CategoryRequest categoryRequest) {
        Category category = MapperHelper.modelMapper().map(categoryRequest, Category.class);
        this.categoryRepository.save(category);
    }

    @Override
    public void update(CategoryRequest categoryRequest, Long categoryCode) {
        Category category = MapperHelper.modelMapper().map(categoryRequest, Category.class);
        this.categoryRepository.save(category);
    }

    @Override
    public void delete(Long categoryCode) {
        categoryRepository.deleteById(categoryCode);
    }

    @Override
    public boolean existsByCategoryCode(Long categoryCode) {
        return categoryRepository.existsByCategoryCode(categoryCode);
    }

    @Override
    public boolean existsByCategoryName(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }


}
