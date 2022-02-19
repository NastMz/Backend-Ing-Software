package com.zhopy.userservice.service.implementation;

import com.zhopy.userservice.repository.RoleRepository;
import com.zhopy.userservice.service.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleImplement implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean existsByRoleCode(Long roleCode) {
        return roleRepository.existsByRoleCode(roleCode);
    }

}
