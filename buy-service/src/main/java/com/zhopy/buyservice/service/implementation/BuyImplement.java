package com.zhopy.buyservice.service.implementation;

import com.zhopy.buyservice.dto.BuyDTO;
import com.zhopy.buyservice.dto.BuyRequest;
import com.zhopy.buyservice.entity.Buy;
import com.zhopy.buyservice.feignclients.ShoesBoughtFeignClient;
import com.zhopy.buyservice.feignclients.ShoesFeignClient;
import com.zhopy.buyservice.feignclients.UserFeignClient;
import com.zhopy.buyservice.model.ShoesBought;
import com.zhopy.buyservice.model.ShoesBoughtRequests;
import com.zhopy.buyservice.repository.BuyRepository;
import com.zhopy.buyservice.service.interfaces.IBuyService;
import com.zhopy.buyservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("BuyService")
public class BuyImplement implements IBuyService {

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ShoesBoughtFeignClient shoesBoughtFeignClient;

    @Autowired
    private ShoesFeignClient shoesFeignClient;

    @Override
    public List<BuyDTO> findAll() {
        List<BuyDTO> dto = new ArrayList<>();
        Iterable<Buy> buys = buyRepository.findAll();

        for (Buy buy : buys) {
            BuyDTO buyDTO = MapperHelper.modelMapper().map(buy, BuyDTO.class);
            dto.add(buyDTO);
        }

        return dto;
    }

    @Override
    public BuyDTO findByBuyNumber(Long buyNumber) {
        Optional<Buy> buy = buyRepository.findById(buyNumber);
        if (!buy.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(buy.get(), BuyDTO.class);
    }

    @Override
    public Long save(BuyRequest buyRequest) {
        Buy buy = MapperHelper.modelMapper().map(buyRequest, Buy.class);
        return buyRepository.save(buy).getBuyNumber();
    }

    @Override
    public void saveShoes(List<ShoesBought> list, Long buyNumber) {
        ShoesBoughtRequests shoesRequest = new ShoesBoughtRequests();
        List<ShoesBought> shoesList = new ArrayList<>();
        for (ShoesBought shoe : list) {
            shoe.setBuyNumber(buyNumber);
            shoesList.add(shoe);
        }
        shoesRequest.setShoesList(shoesList);
        try {
            shoesBoughtFeignClient.save(shoesRequest);
        } catch (Exception e) {

        }

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

    @Override
    public boolean existsByShoeCode(String shoeCode) {
        return shoesFeignClient.existsByShoeCode(shoeCode);
    }


    @Override
    public boolean existsByUserId(String userId) {
        boolean exists;
        try {
            exists = userFeignClient.existsByUserId(userId);
        } catch (Exception e) {
            return false;
        }
        return exists;
    }

}
