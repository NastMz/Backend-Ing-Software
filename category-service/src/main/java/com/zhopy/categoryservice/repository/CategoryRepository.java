package com.zhopy.categoryservice.repository;

import com.zhopy.categoryservice.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Transactional(readOnly = true)
    Optional<Category> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);
    boolean existsByCategoryCode(Long categoryCode);
}
