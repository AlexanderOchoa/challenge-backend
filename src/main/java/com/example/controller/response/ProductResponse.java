package com.example.controller.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponse {

    private Long idProduct;
    private String name;
    private String description;
    private Double price;
    private String creationDate;
    private Integer state;
}
