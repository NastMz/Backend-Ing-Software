package com.zhopy.shoesservice.service.implementation;

import com.zhopy.shoesservice.dto.ShoesDTO;
import com.zhopy.shoesservice.dto.ShoesRequest;
import com.zhopy.shoesservice.entity.Shoes;
import com.zhopy.shoesservice.repository.ShoesRepository;
import com.zhopy.shoesservice.service.interfaces.IShoesService;
import com.zhopy.shoesservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ShoesImplement implements IShoesService {

    @Autowired
    private ShoesRepository shoesRepository;

    @Override
    public List<ShoesDTO> findAll() {
        List<ShoesDTO> dto = new ArrayList<>();
        Iterable<Shoes> shoes = this.shoesRepository.findAll();

        for (Shoes shoe : shoes) {
            ShoesDTO shoesDTO = MapperHelper.modelMapper().map(shoe, ShoesDTO.class);
            dto.add(shoesDTO);
        }

        return dto;
    }

    @Override
    public ShoesDTO findByShoeCode(String shoeCode) {
        Optional<Shoes> shoe = this.shoesRepository.findById(shoeCode);
        if (!shoe.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(shoe.get(), ShoesDTO.class);
    }

    @Override
    public ShoesDTO findByShoeName(String shoeName) {
        Optional<Shoes> shoe = this.shoesRepository.findByShoeName(shoeName);
        if (shoe.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(shoe, ShoesDTO.class);
    }

    @Override
    public void save(ShoesRequest shoesRequest) {
        Shoes shoe = MapperHelper.modelMapper().map(shoesRequest, Shoes.class);
        this.shoesRepository.save(shoe);
    }

    @Override
    public void update(ShoesRequest shoesRequest, String shoeCode) {
        Optional<Shoes> shoeSearch = this.shoesRepository.findById(shoeCode);
        Shoes shoe = shoeSearch.get();
        shoe.setShoeName(shoesRequest.getShoeName());
        this.shoesRepository.save(shoe);

    }

    @Override
    public void delete(String shoeCode) {
        shoesRepository.deleteById(shoeCode);
    }

    @Override
    public boolean existsByShoeCode(String shoeCode) {
        return shoesRepository.existsByShoeCode(shoeCode);
    }

    @Override
    public boolean existsByShoeName(String shoeName) {
        return shoesRepository.existsByShoeName(shoeName);
    }

}
