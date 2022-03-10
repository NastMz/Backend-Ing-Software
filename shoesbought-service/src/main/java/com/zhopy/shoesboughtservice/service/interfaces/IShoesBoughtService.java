package com.zhopy.shoesboughtservice.service.interfaces;

import com.zhopy.shoesboughtservice.dto.ShoesBoughtDTO;
import com.zhopy.shoesboughtservice.dto.ShoesBoughtRequests;
import org.springframework.stereotype.Service;

@Service
public interface IShoesBoughtService {

    void save(ShoesBoughtRequests shoesRequest);

    ShoesBoughtDTO findByShoeCode(String shoeCode);

    ShoesBoughtDTO findByBuyNumber(Long buyNumber);

    boolean existsByShoeCode(String shoeCode);

    boolean existsByBuyNumber(Long buyNumber);
}
