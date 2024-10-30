package com.example.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SaveOrderRequest {

    private Long idClient;
    private List<SaveOrderDetailRequest> products;
}
