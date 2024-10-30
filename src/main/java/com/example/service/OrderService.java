package com.example.service;

import com.example.controller.request.SaveOrderRequest;
import com.example.controller.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse register(SaveOrderRequest request);
    List<OrderResponse> list();
    OrderResponse get(Long idOrder);
    OrderResponse getByIdClient(Long idClient);
    OrderResponse delete(Long idOrder);
}
