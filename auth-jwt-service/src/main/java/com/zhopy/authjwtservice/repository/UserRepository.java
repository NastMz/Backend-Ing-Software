package com.zhopy.authjwtservice.repository;

import com.zhopy.authjwtservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
