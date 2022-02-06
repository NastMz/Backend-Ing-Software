package com.zhopy.authjwtservice.services.interfaces;

import com.zhopy.authjwtservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    User getByEmail(String email);
    boolean existsByEmail(String email);
}
