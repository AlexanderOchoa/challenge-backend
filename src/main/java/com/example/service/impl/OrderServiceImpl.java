package com.example.service.impl;

import com.example.controller.request.SaveOrderDetailRequest;
import com.example.controller.request.SaveOrderRequest;
import com.example.controller.request.UpdateProductRequest;
import com.example.controller.response.OrderDetailResponse;
import com.example.controller.response.OrderResponse;
import com.example.entity.Client;
import com.example.entity.Order;
import com.example.entity.OrderProduct;
import com.example.entity.Product;
import com.example.exception.CustomErrorException;
import com.example.repository.ClientRepository;
import com.example.repository.OrderProductRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ClientRepository clientRepository,
                            ProductRepository productRepository,
                            OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public OrderResponse register(SaveOrderRequest request) {
        Order orderToSave = getOrder(request);

        Order registeredOrder = orderRepository.save(orderToSave);

        List<OrderProduct> orderProducts = new ArrayList<>();

        for (SaveOrderDetailRequest item : request.getProducts()) {
            OrderProduct orderProduct = getOrderProduct(registeredOrder.getIdOrder(), item.getIdProduct(), item.getQuantity());
            OrderProduct orderProductRegistered = orderProductRepository.save(orderProduct);
            orderProducts.add(orderProductRegistered);
        }

        registeredOrder.setProducts(orderProducts);
        registeredOrder = orderRepository.save(registeredOrder);

        return getGetOrderResponse(registeredOrder);
    }

    @Override
    public List<OrderResponse> list() {
        List<Order> orderInDb = orderRepository.findAll();

        return orderInDb.stream()
                .map(this::getGetOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse get(Long idOrder) {
        Optional<Order> orderInDb = orderRepository.findById(idOrder);

        if (!orderInDb.isPresent()) {
            throw new CustomErrorException("The order don't exist.");
        }

        return getGetOrderResponse(orderInDb.get());
    }

    @Override
    public OrderResponse getByIdClient(Long idClient) {
        Optional<Client> clientInDb = clientRepository.findById(idClient);
        Optional<Order> orderInDb = orderRepository.findByClient(clientInDb.get());

        if (!orderInDb.isPresent()) {
            throw new CustomErrorException("The order don't exist.");
        }

        return getGetOrderResponse(orderInDb.get());
    }

    @Override
    public OrderResponse delete(Long idOrder) {
        Optional<Order> orderInDb = orderRepository.findById(idOrder);

        if (!orderInDb.isPresent()) {
            throw new CustomErrorException("The product don't exist.");
        }

        orderInDb.get().setState(0);
        Order orderUpdated = orderRepository.save(orderInDb.get());

        return getGetOrderResponse(orderUpdated);
    }

    private Order getOrder(SaveOrderRequest request) {
        Optional<Client> clientInDb = clientRepository.findById(request.getIdClient());
        double totalPrice = 0.0;

        for (SaveOrderDetailRequest item : request.getProducts()) {
            Optional<Product> productInDb = productRepository.findById(item.getIdProduct());
            totalPrice += (productInDb.get().getPrice() * item.getQuantity());
        }

        Order order = new Order();
        order.setClient(clientInDb.get());
        order.setTotal(totalPrice);
        order.setGenerationDate(new Date());
        order.setState(1);

        return order;
    }

    private OrderProduct getOrderProduct(Long idOrder, Long idProduct, Integer quantity) {
        Optional<Order> orderInDb = orderRepository.findById(idOrder);
        Optional<Product> productInDb = productRepository.findById(idProduct);

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderd(orderInDb.get());
        orderProduct.setProductd(productInDb.get());
        orderProduct.setQuantity(quantity);

        return orderProduct;
    }

    private OrderResponse getGetOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        BeanUtils.copyProperties(order, response);
        response.setIdClient(order.getClient().getIdClient());
        response.setGenerationDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(order.getGenerationDate()));

        List<OrderDetailResponse> orderDetailResponses = order.getProducts().stream()
                .map(orderProduct -> {
                    OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
                    orderDetailResponse.setIdProduct(orderProduct.getProductd().getIdProduct());
                    orderDetailResponse.setQuantity(orderProduct.getQuantity());
                    return orderDetailResponse;
                })
                .collect(Collectors.toList());

        response.setProducts(orderDetailResponses);

        return response;
    }
}
