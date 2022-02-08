package com.zhopy.cityservice.service.interfaces;

import com.zhopy.cityservice.dto.CityDTO;
import com.zhopy.cityservice.dto.CityRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICityService {
    List<CityDTO> findAll();
    CityDTO findByCityCode(Long cityCode);
    CityDTO findByCityName(String cityName);
    void save(CityRequest cityRequest);
    void update(CityRequest cityRequest, Long cityCode);
    void delete(Long cityCode);
    boolean existsByCityCode(Long cityCode);
    boolean existsByCityName(String cityName);
}
