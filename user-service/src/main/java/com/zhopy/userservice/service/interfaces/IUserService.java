package com.zhopy.userservice.service.interfaces;

import com.zhopy.userservice.dto.UserDTO;
import com.zhopy.userservice.dto.UserRequestRegister;
import com.zhopy.userservice.dto.UserRequestUpdate;
import com.zhopy.userservice.entity.User;
import com.zhopy.userservice.model.Question;
import com.zhopy.userservice.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    List<UserDTO> findAll();

    UserDTO findByUserId(String userId);

    User findByEmail(String email);

    void save(UserRequestRegister userRequestRegister);

    void update(UserRequestUpdate userRequestUpdate, String userId);

    void delete(String userId);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    Role findByRoleCode(Long roleCode);

    boolean existsByRoleCode(Long roleCode);

    Question findByQuestionCode(Long questionCode);

    boolean existsByQuestionCode(Long questionCode);
}
