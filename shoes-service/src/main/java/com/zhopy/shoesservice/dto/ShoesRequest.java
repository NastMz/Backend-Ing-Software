package com.zhopy.shoesservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShoesRequest implements Serializable {
    private String shoeCode;
    private String shoeName;
    private String price;
    private String stock;
    private String description;
    private String imageName;
    private byte[] imageBytes;
    private Long categoryCode;
    private Long supplierNit;
}
