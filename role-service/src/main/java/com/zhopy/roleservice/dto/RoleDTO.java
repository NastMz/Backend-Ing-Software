package com.zhopy.roleservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {
    private Long roleCode;
    private String roleName;
}