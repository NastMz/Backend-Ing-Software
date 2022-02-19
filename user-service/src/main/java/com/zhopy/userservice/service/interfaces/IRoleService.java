package com.zhopy.userservice.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface IRoleService {
    boolean existsByRoleCode(Long roleCode);
}
