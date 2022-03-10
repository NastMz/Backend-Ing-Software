package com.zhopy.shoesboughtservice.service.implementation;

import com.zhopy.shoesboughtservice.dto.ShoesBoughtDTO;
import com.zhopy.shoesboughtservice.dto.ShoesBoughtRequests;
import com.zhopy.shoesboughtservice.entity.ShoesBought;
import com.zhopy.shoesboughtservice.feignclients.BuyFeignClient;
import com.zhopy.shoesboughtservice.feignclients.ShoesFeignClient;
import com.zhopy.shoesboughtservice.repository.ShoesBoughtRepository;
import com.zhopy.shoesboughtservice.service.interfaces.IShoesBoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
@Qualifier("ShoesBoughtService")
public class ShoesBoughtImplement implements IShoesBoughtService {

    @Autowired
    private ShoesBoughtRepository shoesBoughtRepository;

    @Autowired
    private ShoesFeignClient shoesFeignClient;

    @Autowired
    private BuyFeignClient buyFeignClient;


    @Override
    public void save(ShoesBoughtRequests shoesRequest) {
        Iterable<ShoesBought> shoesBoughts = new ArrayList<>(shoesRequest.getShoesList());
        this.shoesBoughtRepository.saveAll(shoesBoughts);

    }

    @Override
    public ShoesBoughtDTO findByShoeCode(String shoeCode) {
        Iterable<ShoesBought> shoes = this.shoesBoughtRepository.findAllByShoeCode(shoeCode);
        List<ShoesBought> shoesList = new ArrayList<>();
        for (ShoesBought shoe : shoes) {
            shoesList.add(shoe);
        }
        ShoesBoughtDTO shoesDto = new ShoesBoughtDTO();
        shoesDto.setShoesList(shoesList);
        return shoesDto;
    }

    @Override
    public ShoesBoughtDTO findByBuyNumber(Long buyNumber) {
        Iterable<ShoesBought> shoes = this.shoesBoughtRepository.findAllByBuyNumber(buyNumber);
        List<ShoesBought> shoesList = new ArrayList<>();
        for (ShoesBought shoe : shoes) {
            shoesList.add(shoe);
        }
        ShoesBoughtDTO shoesDto = new ShoesBoughtDTO();
        shoesDto.setShoesList(shoesList);
        return shoesDto;
    }

    @Override
    public boolean existsByShoeCode(String shoeCode) {
        return shoesFeignClient.existsByShoeCode(shoeCode);
    }

    @Override
    public boolean existsByBuyNumber(Long buyNumber) {
        return buyFeignClient.existsByBuyNumber(buyNumber);
    }
}
