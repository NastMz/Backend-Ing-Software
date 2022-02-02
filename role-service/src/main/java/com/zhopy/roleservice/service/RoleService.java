package com.zhopy.roleservice.service;

import com.zhopy.roleservice.entity.Role;
import com.zhopy.roleservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> getById(Long roleCode) {
        return roleRepository.findById(roleCode);
    }

    public Optional<Role> getByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public void delete(Long roleCode) {
        roleRepository.deleteById(roleCode);
    }

    public boolean existsById(Long roleCode) {
        return roleRepository.existsById(roleCode);
    }

    public boolean existsByName(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }
}
