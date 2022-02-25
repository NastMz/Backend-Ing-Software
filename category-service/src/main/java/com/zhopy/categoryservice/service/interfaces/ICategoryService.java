package com.zhopy.categoryservice.service.interfaces;

import com.zhopy.categoryservice.dto.CategoryDTO;
import com.zhopy.categoryservice.dto.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO findByCategoryCode(Long categoryCode);

    CategoryDTO findByCategoryName(String categoryName);

    void save(CategoryRequest categoryRequest);

    void update(CategoryRequest categoryRequest, Long categoryCode);

    void delete(Long categoryCode);

    boolean existsByCategoryCode(Long categoryCode);

    boolean existsByCategoryName(String categoryName);
}
