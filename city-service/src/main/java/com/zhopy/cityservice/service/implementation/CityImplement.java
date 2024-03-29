package com.zhopy.cityservice.service.implementation;

import com.zhopy.cityservice.dto.CityDTO;
import com.zhopy.cityservice.dto.CityRequest;
import com.zhopy.cityservice.entity.City;
import com.zhopy.cityservice.repository.CityRepository;
import com.zhopy.cityservice.service.interfaces.ICityService;
import com.zhopy.cityservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("CityService")
public class CityImplement implements ICityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<CityDTO> findAll() {
        List<CityDTO> dto = new ArrayList<>();
        Iterable<City> cities = this.cityRepository.findAll();

        for (City city : cities) {
            CityDTO cityDTO = MapperHelper.modelMapper().map(city, CityDTO.class);
            dto.add(cityDTO);
        }

        return dto;
    }

    @Override
    public CityDTO findByCityCode(Long cityCode) {
        Optional<City> city = this.cityRepository.findById(cityCode);
        if (!city.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(city.get(), CityDTO.class);
    }

    @Override
    public CityDTO findByCityName(String cityName) {
        Optional<City> city = this.cityRepository.findByCityName(cityName);
        if (city.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(city, CityDTO.class);
    }

    @Override
    public void save(CityRequest cityRequest) {
        City city = MapperHelper.modelMapper().map(cityRequest, City.class);
        this.cityRepository.save(city);
    }

    @Override
    public void update(CityRequest cityRequest, Long cityCode) {
        City city = MapperHelper.modelMapper().map(cityRequest, City.class);
        this.cityRepository.save(city);
    }

    @Override
    public void delete(Long cityCode) {
        cityRepository.deleteById(cityCode);
    }

    @Override
    public boolean existsByCityCode(Long cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }

    @Override
    public boolean existsByCityName(String roleName) {
        return cityRepository.existsByCityName(roleName);
    }

}
