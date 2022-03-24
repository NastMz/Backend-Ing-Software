package com.zhopy.orderservice.service.implementation;

import com.zhopy.orderservice.dto.OrderDTO;
import com.zhopy.orderservice.dto.OrderRequest;
import com.zhopy.orderservice.entity.Order;
import com.zhopy.orderservice.feignclients.BuyFeignClient;
import com.zhopy.orderservice.feignclients.StatusFeignClient;
import com.zhopy.orderservice.feignclients.UserFeignClient;
import com.zhopy.orderservice.repository.OrderRepository;
import com.zhopy.orderservice.service.interfaces.IOrderService;
import com.zhopy.orderservice.utils.helpers.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Qualifier("OrderService")
public class OrderImplement implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private StatusFeignClient statusFeignClient;

    @Autowired
    private BuyFeignClient buyFeignClient;

    @Override
    public List<OrderDTO> findAll() {
        List<OrderDTO> dto = new ArrayList<>();
        Iterable<Order> orders = this.orderRepository.findAll();

        for (Order order : orders) {
            OrderDTO orderDTO = MapperHelper.modelMapper().map(order, OrderDTO.class);
            dto.add(orderDTO);
        }

        return dto;
    }

    @Override
    public OrderDTO findByOrderNumber(Long orderNumber) {
        Optional<Order> order = this.orderRepository.findById(orderNumber);
        if (!order.isPresent()) {
            return null;
        }
        return MapperHelper.modelMapper().map(order.get(), OrderDTO.class);
    }

    @Override
    public void save(OrderRequest orderRequest) {
        Order order = MapperHelper.modelMapper().map(orderRequest, Order.class);
        this.orderRepository.save(order);
    }

    @Override
    public void update(OrderRequest orderRequest, Long orderNumber) {
        Order order = MapperHelper.modelMapper().map(orderRequest, Order.class);
        this.orderRepository.save(order);

    }

    @Override
    public void delete(Long orderNumber) {
        orderRepository.deleteById(orderNumber);
    }

    @Override
    public boolean existsByOrderNumber(Long orderNumber) {
        return orderRepository.existsByOrderNumber(orderNumber);
    }

    @Override
    public boolean existsByBuyNumber(Long buyNumber) {
        boolean exists;
        try {
            exists = buyFeignClient.existsByBuyNumber(buyNumber);
        } catch (Exception e) {
            return false;
        }
        return exists;
    }

    @Override
    public boolean existsByStatusCode(Long statusCode) {
        boolean exists;
        try {
            exists = statusFeignClient.existsByStatusCode(statusCode);
        } catch (Exception e) {
            return false;
        }
        return exists;
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

    @Override
    public List<OrderDTO> findAllByUserId(String userId) {
        List<OrderDTO> dto = new ArrayList<>();
        Iterable<Order> orders = this.orderRepository.findAll();

        for (Order order : orders) {
            if (order.getUserId().equals(userId)) {
                OrderDTO orderDTO = MapperHelper.modelMapper().map(order, OrderDTO.class);
                dto.add(orderDTO);
            }
        }

        return dto;
    }

}
