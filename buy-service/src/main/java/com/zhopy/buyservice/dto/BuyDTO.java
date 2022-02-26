package com.zhopy.buyservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class BuyDTO implements Serializable {
    private Long buyNumber;
    private Long total;
    private Date buyDate;
    private String userId;
}