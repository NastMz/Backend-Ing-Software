package com.zhopy.supplierservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupplierDTO implements Serializable {
    private String supplierNit;
    private String supplierName;
    private String supplierAddress;
    private String phone;
    private String cityName;
}
