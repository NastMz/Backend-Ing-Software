package com.zhopy.authjwtservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private String userName;
    private String email;
    private Long roleCode;
}
