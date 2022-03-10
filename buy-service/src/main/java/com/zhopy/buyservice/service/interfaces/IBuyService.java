package com.zhopy.buyservice.service.interfaces;

import com.zhopy.buyservice.dto.BuyDTO;
import com.zhopy.buyservice.dto.BuyRequest;
import com.zhopy.buyservice.model.ShoesBought;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBuyService {
    List<BuyDTO> findAll();

    BuyDTO findByBuyNumber(Long buyNumber);

    Long save(BuyRequest buyRequest);

    void saveShoes(List<ShoesBought> list, Long buyNumber);

    void update(BuyRequest buyRequest, Long buyNumber);

    void delete(Long buyNumber);

    boolean existsByBuyNumber(Long buyNumber);

    boolean existsByShoeCode(String shoeCode);

    boolean existsByUserId(String userId);
}
