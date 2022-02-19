package com.zhopy.shoesservice.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface ICategoryService {
    boolean existsByCategoryCode(Long categoryCode);
}
