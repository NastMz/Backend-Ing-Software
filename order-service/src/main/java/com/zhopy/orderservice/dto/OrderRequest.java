package com.zhopy.orderservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class OrderRequest implements Serializable {
    private Long orderNumber;
    private Date orderDate;
    private String userId;
    private Long statusCode;
    private Long buyNumber;
}