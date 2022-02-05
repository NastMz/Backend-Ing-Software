package com.zhopy.roleservice.validator;

import com.zhopy.roleservice.dto.RoleRequest;
import com.zhopy.roleservice.service.interfaces.IRoleService;
import com.zhopy.roleservice.utils.exeptions.ApiNotFound;
import com.zhopy.roleservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleValidatorImplement implements RoleValidator {

    @Autowired
    private IRoleService roleService;

    @Override
    public void validator(RoleRequest request) throws ApiUnprocessableEntity {
        if (roleService.existsByRoleCode(request.getRoleCode())) {
            this.message422("El codigo ya existe");
        }
        if (roleService.existsByRoleName(request.getRoleName())){
            this.message422("El nombre ya existe");
        }
    }

    @Override
    public void validatorById(Long id) throws ApiNotFound {
        if (!roleService.existsByRoleCode(id)){
            this.message404("El codigo no existe");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
