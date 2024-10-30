package com.example.service;

import com.example.controller.request.SaveClientRequest;
import com.example.controller.request.UpdateClientRequest;
import com.example.controller.response.ClientResponse;

import java.util.List;

public interface ClientService {
    ClientResponse register(SaveClientRequest request);
    List<ClientResponse> list();
    ClientResponse get(Long idClient);
    ClientResponse update(UpdateClientRequest request);
    ClientResponse delete(Long idClient);
}
