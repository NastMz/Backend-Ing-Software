package com.zhopy.supplierservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable {
    private Long cityCode;
    private String cityName;
}