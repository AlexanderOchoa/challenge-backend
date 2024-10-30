package com.example.controller.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientResponse {

    private Long idClient;
    private String name;
    private String email;
    private String phone;
    private Integer state;
}
