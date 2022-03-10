package com.zhopy.shoesboughtservice.dto;

import com.zhopy.shoesboughtservice.entity.ShoesBought;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShoesBoughtDTO implements Serializable {
    private List<ShoesBought> shoesList;
}