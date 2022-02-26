package com.zhopy.roleservice.validator;

import com.zhopy.roleservice.dto.RoleDTO;
import com.zhopy.roleservice.dto.RoleRequest;
import com.zhopy.roleservice.service.interfaces.IRoleService;
import com.zhopy.roleservice.utils.exeptions.ApiNotFound;
import com.zhopy.roleservice.utils.exeptions.ApiUnprocessableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RoleValidatorImplement implements RoleValidator {

    @Autowired
    @Qualifier("RoleService")
    private IRoleService roleService;

    @Override
    public void validator(RoleRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        if (roleService.existsByRoleName(request.getRoleName())) {
            this.message422("El nombre ya existe");
        }
    }

    @Override
    public void validatorUpdate(RoleRequest request) throws ApiUnprocessableEntity {
        validateData(request);
        validateName(request);
    }

    @Override
    public void validatorById(Long id) throws ApiNotFound {
        if (!roleService.existsByRoleCode(id)) {
            this.message404("El codigo no existe");
        }
    }

    @Override
    public void validatorByIdRequest(Long urlCode, Long roleCode) throws ApiNotFound, ApiUnprocessableEntity {
        if (!roleService.existsByRoleCode(urlCode)) {
            this.message404("El codigo no existe");
        }
        if (!urlCode.equals(roleCode)) {
            this.message422("El codigo del cuerpo de la peticion no coincide con el de la uri");
        }
    }

    private void validateData(RoleRequest request) throws ApiUnprocessableEntity {
        if (request.getRoleName() == null || request.getRoleName().isBlank()) {
            this.message422("El nombre no puede estar vacio");
        }
    }

    private void validateName(RoleRequest roleRequest) throws ApiUnprocessableEntity {
        RoleDTO roleSearch = this.roleService.findByRoleCode(roleRequest.getRoleCode());
        if (!roleRequest.getRoleName().equals(roleSearch.getRoleName()) && roleService.existsByRoleName(roleRequest.getRoleName())) {
            this.message422("El nombre ya existe");
        }
    }

    private void message422(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    private void message404(String message) throws ApiNotFound {
        throw new ApiNotFound(message);
    }
}
