package com.zhopy.userservice.service.implementation;

import com.zhopy.userservice.dto.UserDTO;
import com.zhopy.userservice.dto.UserRequest;
import com.zhopy.userservice.entity.User;
import com.zhopy.userservice.feignclients.RoleFeignClient;
import com.zhopy.userservice.model.Role;
import com.zhopy.userservice.repository.UserRepository;
import com.zhopy.userservice.service.interfaces.IUserService;
import com.zhopy.userservice.utils.hash.BCrypt;
import com.zhopy.userservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("UserService")
public class UserImplement implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleFeignClient roleFeignClient;

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> dto = new ArrayList<>();
        Iterable<User> users = this.userRepository.findAll();

        for (User user : users) {
            UserDTO userDTO = MapperHelper.modelMapper().map(user, UserDTO.class);
            userDTO.setRoleName(findByRoleCode(user.getRoleCode()).getRoleName());
            dto.add(userDTO);
        }

        return dto;
    }

    @Override
    public UserDTO findByUserId(String userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (!user.isPresent()) {
            return null;
        }
        UserDTO userDTO = MapperHelper.modelMapper().map(user.get(), UserDTO.class);
        userDTO.setRoleName(findByRoleCode(user.get().getRoleCode()).getRoleName());
        return userDTO;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return null;
        }
        return user.get();
    }

    @Override
    public void save(UserRequest userRequest) {
        User user = MapperHelper.modelMapper().map(userRequest, User.class);
        user.setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()));
        this.userRepository.save(user);
    }

    @Override
    public void delete(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void update(UserRequest userRequest, String userId) {
        Optional<User> userSearch = this.userRepository.findById(userId);
        User user = MapperHelper.modelMapper().map(userRequest, User.class);
        if (StringUtils.hasText(userRequest.getPassword()) && !userRequest.getPassword().equals(userSearch.get().getPassword())) {
            user.setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()));
        } else {
            user.setPassword(userSearch.get().getPassword());
        }

        this.userRepository.save(user);

    }

    @Override
    public boolean existsByUserId(String userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Role findByRoleCode(Long roleCode) {
        return roleFeignClient.findByRoleCode(roleCode);
    }

    @Override
    public boolean existsByRoleCode(Long roleCode) {
        return roleFeignClient.existsByRoleCode(roleCode);
    }

}
