package com.zhopy.shoesservice.service.interfaces;

import com.zhopy.shoesservice.dto.ShoesDTO;
import com.zhopy.shoesservice.dto.ShoesRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IShoesService {
    List<ShoesDTO> findAll();

    ShoesDTO findByShoeCode(String shoeCode);

    ShoesDTO findByShoeName(String shoeName);

    void save(ShoesRequest roleRequest);

    void update(ShoesRequest roleRequest, String shoeCode);

    void delete(String shoeCode);

    boolean existsByShoeCode(String shoeCode);

    boolean existsByShoeName(String shoeName);
}
