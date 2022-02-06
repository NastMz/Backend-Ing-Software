package com.zhopy.userservice.service.interfaces;

import com.zhopy.userservice.dto.UserDTO;
import com.zhopy.userservice.dto.UserRequest;
import com.zhopy.userservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    List<UserDTO> findAll();
    UserDTO findByUserId(String userId);
    UserDTO findByEmail(String email);
    User getByEmail(String email);
    void save(UserRequest userRequest);
    void update(UserRequest userRequest, String userId);
    void delete(String userId);
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
}
