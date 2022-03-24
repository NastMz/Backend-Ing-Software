package com.zhopy.orderservice.service.interfaces;

import com.zhopy.orderservice.dto.OrderDTO;
import com.zhopy.orderservice.dto.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    List<OrderDTO> findAll();

    OrderDTO findByOrderNumber(Long orderNumber);

    void save(OrderRequest orderRequest);

    void update(OrderRequest orderRequest, Long orderNumber);

    void delete(Long orderNumber);

    boolean existsByOrderNumber(Long orderNumber);

    boolean existsByBuyNumber(Long buyNumber);

    boolean existsByStatusCode(Long statusCode);

    boolean existsByUserId(String userId);

    List<OrderDTO> findAllByUserId(String userId);
}
