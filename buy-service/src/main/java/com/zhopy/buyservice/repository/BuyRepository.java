package com.zhopy.buyservice.repository;

import com.zhopy.buyservice.entity.Buy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BuyRepository extends CrudRepository<Buy, Long> {
    @Transactional(readOnly = true)
    Optional<Buy> findByBuyNumber(Long buyNumber);

    boolean existsByBuyNumber(Long buyNumber);

    Optional<Buy> findByUserId(String userId);
}
