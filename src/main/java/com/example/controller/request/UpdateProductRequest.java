package com.example.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UpdateProductRequest {

    @NotNull(message = "name required")
    private Long idProduct;
    private String name;
    private String description;
    private Double price;
}
