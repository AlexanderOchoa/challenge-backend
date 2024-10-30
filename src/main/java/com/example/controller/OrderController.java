package com.example.controller;

import com.example.controller.request.SaveOrderRequest;
import com.example.controller.response.OrderResponse;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<OrderResponse> register(@Valid @RequestBody SaveOrderRequest request) {
        OrderResponse response = orderService.register(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody ResponseEntity<List<OrderResponse>> list() {
        List<OrderResponse> response = orderService.list();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{idOrder}", produces = "application/json")
    public @ResponseBody ResponseEntity<OrderResponse> get(@PathVariable(value = "idOrder") Long idOrder) {
        OrderResponse response = orderService.get(idOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/client/{idClient}", produces = "application/json")
    public @ResponseBody ResponseEntity<OrderResponse> getbyIdClient(@PathVariable(value = "idClient") Long idClient) {
        OrderResponse response = orderService.getByIdClient(idClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idOrder}", produces = "application/json")
    public @ResponseBody ResponseEntity<OrderResponse> delete(@PathVariable(value = "idOrder") Long idOrder) {
        OrderResponse response = orderService.delete(idOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
