package com.zhopy.roleservice.service.interfaces;

import com.zhopy.roleservice.dto.RoleDTO;
import com.zhopy.roleservice.dto.RoleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRoleService {
    List<RoleDTO> findAll();

    RoleDTO findByRoleCode(Long roleCode);

    RoleDTO findByRoleName(String roleName);

    void save(RoleRequest roleRequest);

    void update(RoleRequest roleRequest, Long roleCode);

    void delete(Long roleCode);

    boolean existsByRoleCode(Long roleCode);

    boolean existsByRoleName(String roleName);
}
