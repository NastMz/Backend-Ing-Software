package com.zhopy.statusservice.service.interfaces;

import com.zhopy.statusservice.dto.StatusDTO;
import com.zhopy.statusservice.dto.StatusRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IStatusService {
    List<StatusDTO> findAll();

    StatusDTO findByStatusCode(Long statusCode);

    StatusDTO findByStatusName(String statusName);

    void save(StatusRequest statusRequest);

    void update(StatusRequest statusRequest, Long statusCode);

    void delete(Long statusCode);

    boolean existsByStatusCode(Long statusCode);

    boolean existsByStatusName(String statusName);
}
