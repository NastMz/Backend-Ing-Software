package com.zhopy.categoryservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryRequest implements Serializable {
    private long categoryCode;
    private String categoryName;
}
