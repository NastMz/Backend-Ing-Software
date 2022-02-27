package com.zhopy.authjwtservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String userName;
    private String email;
    private String address;
    private String phone;
    private Long roleCode;
}
