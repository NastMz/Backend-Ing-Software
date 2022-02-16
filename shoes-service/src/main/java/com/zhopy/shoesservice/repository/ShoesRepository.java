package com.zhopy.shoesservice.repository;

import com.zhopy.shoesservice.entity.Shoes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ShoesRepository extends CrudRepository<Shoes, String> {
    @Transactional(readOnly = true)
    Optional<Shoes> findByShoeName(String shoeName);

    boolean existsByShoeName(String shoeName);

    boolean existsByShoeCode(String shoeCode);
}
