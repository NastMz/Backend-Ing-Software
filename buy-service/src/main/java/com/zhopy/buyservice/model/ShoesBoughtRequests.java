package com.zhopy.buyservice.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShoesBoughtRequests implements Serializable {
    private List<ShoesBought> shoesList;
}
