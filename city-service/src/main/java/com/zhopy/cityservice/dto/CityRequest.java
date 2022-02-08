package com.zhopy.cityservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityRequest implements Serializable {
    private Long cityCode;
    private String cityName;
}
