package com.zhopy.statusservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatusRequest implements Serializable {
    private Long statusCode;
    private String statusName;
}
