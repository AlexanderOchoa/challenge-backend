package com.example.service;

import com.example.controller.request.SaveProductRequest;
import com.example.controller.request.UpdateProductRequest;
import com.example.controller.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse register(SaveProductRequest request);
    List<ProductResponse> list();
    ProductResponse get(Long idProduct);
    ProductResponse update(UpdateProductRequest request);
    ProductResponse delete(Long idProduct);
}
