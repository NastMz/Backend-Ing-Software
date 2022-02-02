package com.zhopy.userservice.service;

import com.zhopy.userservice.entity.User;
import com.zhopy.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getByEmail(String roleName) {
        return userRepository.findByEmail(roleName);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(String userId) {
        userRepository.deleteById(userId);
    }

    public boolean existsById(String userId) {
        return userRepository.existsById(userId);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
