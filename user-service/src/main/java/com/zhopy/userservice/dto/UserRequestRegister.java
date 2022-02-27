package com.zhopy.userservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRequestRegister implements Serializable {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String secureAnswer;
    private Long roleCode;
    private Long questionCode;
}
