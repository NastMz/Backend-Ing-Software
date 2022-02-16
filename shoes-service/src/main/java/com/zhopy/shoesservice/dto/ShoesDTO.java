package com.zhopy.shoesservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShoesDTO implements Serializable {
    private String shoeCode;
    private String shoeName;
    private String price;
    private String stock;
    private String description;
    private String image;
    private String categoryCode;
    private String supplierNit;
}