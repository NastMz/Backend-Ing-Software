package com.zhopy.buyservice.service.implementation;

import com.zhopy.buyservice.dto.BuyDTO;
import com.zhopy.buyservice.dto.BuyRequest;
import com.zhopy.buyservice.entity.Buy;
import com.zhopy.buyservice.repository.BuyRepository;
import com.zhopy.buyservice.service.interfaces.IBuyService;
import com.zhopy.buyservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BuyImplement implements IBuyService {

    @Autowired
    private BuyRepository buyRepository;

    @Override
    public List<BuyDTO> findAll() {
        List<BuyDTO> dto = new ArrayList<>();
        Iterable<Buy> compras = this.buyRepository.findAll();

        for (Buy buy : compras) {
            BuyDTO comprasDTO = MapperHelper.modelMapper().map(buy, BuyDTO.class);
            dto.add(comprasDTO);
        }

        return dto;
    }

    @Override
    public BuyDTO findByBuyNumber(Long buyNumber) {
        Optional<Buy> buy = this.buyRepository.findById(buyNumber);
        if (!buy.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(buy.get(), BuyDTO.class);
    }

    @Override
    public void save(BuyRequest buyRequest) {
        Buy buy = MapperHelper.modelMapper().map(buyRequest, Buy.class);
        this.buyRepository.save(buy);
    }

    @Override
    public void update(BuyRequest buyRequest, Long buyNumber) {
        Buy buy = MapperHelper.modelMapper().map(buyRequest, Buy.class);
        this.buyRepository.save(buy);

    }

    @Override
    public void delete(Long buyNumber) {
        buyRepository.deleteById(buyNumber);
    }

    @Override
    public boolean existsByBuyNumber(Long buyNumber) {
        return buyRepository.existsByBuyNumber(buyNumber);
    }

}
