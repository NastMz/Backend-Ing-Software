package com.zhopy.userservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRequest implements Serializable {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private Long roleCode;
}
