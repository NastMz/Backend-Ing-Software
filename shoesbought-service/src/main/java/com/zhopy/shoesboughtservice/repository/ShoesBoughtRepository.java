package com.zhopy.shoesboughtservice.repository;

import com.zhopy.shoesboughtservice.entity.ShoesBought;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesBoughtRepository extends CrudRepository<ShoesBought, Long> {
    boolean existsByShoeCode(String shoeCode);

    boolean existsByBuyNumber(Long buyNumber);

    Iterable<ShoesBought> findAllByShoeCode(String shoeCode);

    Iterable<ShoesBought> findAllByBuyNumber(Long buyNumber);
}
