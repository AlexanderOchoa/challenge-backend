package com.example.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UpdateClientRequest {

    @NotNull(message = "name required")
    private Long idClient;

    @NotNull(message = "name required")
    @Length(min = 2, max = 100)
    private String name;

    @NotNull(message = "email required")
    @Email
    private String email;

    @NotNull(message = "username required")
    @Length(min = 9, max = 9)
    private String phone;
}
