package com.example.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SaveOrderDetailRequest {

    private Long idProduct;
    private Integer quantity;
}
