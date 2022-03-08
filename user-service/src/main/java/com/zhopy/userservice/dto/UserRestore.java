package com.zhopy.userservice.dto;

import lombok.Data;

@Data
public class UserRestore {
    private String email;
    private String secureAnswer;
    private String newPassword;
}
