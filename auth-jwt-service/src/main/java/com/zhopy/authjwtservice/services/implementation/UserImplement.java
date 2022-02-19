package com.zhopy.authjwtservice.services.implementation;

import com.zhopy.authjwtservice.entity.User;
import com.zhopy.authjwtservice.repository.UserRepository;
import com.zhopy.authjwtservice.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserImplement implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
