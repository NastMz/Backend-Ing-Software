package com.zhopy.cityservice.repository;

import com.zhopy.cityservice.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    @Transactional(readOnly = true)
    Optional<City> findByCityName(String cityName);

    boolean existsByCityName(String cityName);

    boolean existsByCityCode(Long cityCode);
}
