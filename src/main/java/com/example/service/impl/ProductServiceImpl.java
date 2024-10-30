package com.example.service.impl;

import com.example.controller.request.SaveProductRequest;
import com.example.controller.request.UpdateProductRequest;
import com.example.controller.response.ProductResponse;
import com.example.entity.Product;
import com.example.exception.CustomErrorException;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse register(SaveProductRequest request) {
        Product productToSave = getProduct(request);

        Product registeredProduct = productRepository.save(productToSave);

        return getGetProductResponse(registeredProduct);
    }

    @Override
    public List<ProductResponse> list() {
        List<Product> productInDb = productRepository.findAll();

        return productInDb.stream()
                .map(this::getGetProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse get(Long idProduct) {
        Optional<Product> productInDb = productRepository.findById(idProduct);

        if (!productInDb.isPresent()) {
            throw new CustomErrorException("The product don't exist.");
        }

        return getGetProductResponse(productInDb.get());
    }

    @Override
    public ProductResponse update(UpdateProductRequest request) {
        Optional<Product> productInDb = productRepository.findById(request.getIdProduct());

        if (!productInDb.isPresent()) {
            throw new CustomErrorException("The product don't exist.");
        }

        Product productToSave = getUpdateProduct(request, productInDb.get());

        Product updatedProduct = productRepository.save(productToSave);

        return getGetProductResponse(updatedProduct);
    }

    @Override
    public ProductResponse delete(Long idProduct) {
        Optional<Product> productInDb = productRepository.findById(idProduct);

        if (!productInDb.isPresent()) {
            throw new CustomErrorException("The product don't exist.");
        }

        productInDb.get().setState(0);
        Product productUpdated = productRepository.save(productInDb.get());

        return getGetProductResponse(productUpdated);
    }

    private Product getProduct(SaveProductRequest request) {
        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        product.setState(1);
        product.setCreationDate(new Date());

        return product;
    }

    private ProductResponse getGetProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response);
        response.setCreationDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(product.getCreationDate()));

        return response;
    }

    private Product getUpdateProduct(UpdateProductRequest request, Product product) {
        BeanUtils.copyProperties(request, product);
        return product;
    }

}
