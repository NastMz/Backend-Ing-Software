package com.zhopy.buyservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class BuyRequest implements Serializable {
    private Long buyNumber;
    private Long total;
    private Date buyDate;
    private String userId;
    private List<String> shoesList;
}