package com.zhopy.cityservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityDTO implements Serializable {
    private Long cityCode;
    private String cityName;
}