package com.zhopy.categoryservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
    private long categoryCode;
    private String categoryName;
}
