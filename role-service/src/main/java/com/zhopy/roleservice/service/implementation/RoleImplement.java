package com.zhopy.roleservice.service.implementation;

import com.zhopy.roleservice.dto.RoleDTO;
import com.zhopy.roleservice.dto.RoleRequest;
import com.zhopy.roleservice.entity.Role;
import com.zhopy.roleservice.repository.RoleRepository;
import com.zhopy.roleservice.service.interfaces.IRoleService;
import com.zhopy.roleservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("RoleService")
public class RoleImplement implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDTO> findAll() {
        List<RoleDTO> dto = new ArrayList<>();
        Iterable<Role> roles = this.roleRepository.findAll();

        for (Role role : roles) {
            RoleDTO rolesDTO = MapperHelper.modelMapper().map(role, RoleDTO.class);
            dto.add(rolesDTO);
        }

        return dto;
    }

    @Override
    public RoleDTO findByRoleCode(Long roleCode) {
        Optional<Role> role = this.roleRepository.findById(roleCode);
        if (!role.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(role.get(), RoleDTO.class);
    }

    @Override
    public RoleDTO findByRoleName(String roleName) {
        Optional<Role> role = this.roleRepository.findByRoleName(roleName);
        if (role.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(role, RoleDTO.class);
    }

    @Override
    public void save(RoleRequest roleRequest) {
        Role role = MapperHelper.modelMapper().map(roleRequest, Role.class);
        this.roleRepository.save(role);
    }

    @Override
    public void update(RoleRequest roleRequest, Long roleCode) {
        Role role = MapperHelper.modelMapper().map(roleRequest, Role.class);
        this.roleRepository.save(role);

    }

    @Override
    public void delete(Long roleCode) {
        roleRepository.deleteById(roleCode);
    }

    @Override
    public boolean existsByRoleCode(Long roleCode) {
        return roleRepository.existsByRoleCode(roleCode);
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

}
