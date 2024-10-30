package com.example.controller.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderResponse {

    private Long idOrder;
    private Long idClient;
    private List<OrderDetailResponse> products;
    private Double total;
    private String generationDate;
    private Integer state;
}
