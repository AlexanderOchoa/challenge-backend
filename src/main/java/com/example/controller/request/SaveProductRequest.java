package com.example.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SaveProductRequest {

    @NotNull(message = "name required")
    @Length(min = 2, max = 100)
    private String name;
    private String description;
    private Double price;
}
