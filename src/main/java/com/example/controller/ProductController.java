package com.example.controller;

import com.example.controller.request.SaveProductRequest;
import com.example.controller.request.UpdateProductRequest;
import com.example.controller.response.ProductResponse;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<ProductResponse> register(@Valid @RequestBody SaveProductRequest request) {
        ProductResponse response = productService.register(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody ResponseEntity<List<ProductResponse>> list() {
        List<ProductResponse> response = productService.list();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{idClient}", produces = "application/json")
    public @ResponseBody ResponseEntity<ProductResponse> get(@PathVariable(value = "idClient") Long idClient) {
        ProductResponse response = productService.get(idClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<ProductResponse> update(@Valid @RequestBody UpdateProductRequest request) {
        ProductResponse response = productService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idClient}", produces = "application/json")
    public @ResponseBody ResponseEntity<ProductResponse> delete(@PathVariable(value = "idClient") Long idClient) {
        ProductResponse response = productService.delete(idClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
