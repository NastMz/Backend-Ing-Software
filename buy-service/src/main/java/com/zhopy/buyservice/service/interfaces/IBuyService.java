package com.zhopy.buyservice.service.interfaces;

import com.zhopy.buyservice.dto.BuyDTO;
import com.zhopy.buyservice.dto.BuyRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBuyService {
    List<BuyDTO> findAll();

    BuyDTO findByBuyNumber(Long buyNumber);

    void save(BuyRequest buyRequest);

    void update(BuyRequest buyRequest, Long buyNumber);

    void delete(Long buyNumber);

    boolean existsByBuyNumber(Long buyNumber);
}
