package com.zhopy.userservice.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userId;
    private String userName;
    private String email;
    private String address;
    private String phone;
    private String roleName;
}

