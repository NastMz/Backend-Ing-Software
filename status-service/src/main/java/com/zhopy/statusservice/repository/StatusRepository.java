package com.zhopy.statusservice.repository;

import com.zhopy.statusservice.entity.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
    @Transactional(readOnly = true)
    Optional<Status> findByStatusName(String statusName);

    boolean existsByStatusName(String statusName);

    boolean existsByStatusCode(Long statusCode);
}
