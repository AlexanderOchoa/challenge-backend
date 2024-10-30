package com.example.controller;

import com.example.controller.request.SaveClientRequest;
import com.example.controller.request.UpdateClientRequest;
import com.example.controller.response.ClientResponse;
import com.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<ClientResponse> register(@Valid @RequestBody SaveClientRequest request) {
        ClientResponse response = clientService.register(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody ResponseEntity<List<ClientResponse>> list() {
        List<ClientResponse> response = clientService.list();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{idClient}", produces = "application/json")
    public @ResponseBody ResponseEntity<ClientResponse> get(@PathVariable(value = "idClient") Long idClient) {
        ClientResponse response = clientService.get(idClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<ClientResponse> update(@Valid @RequestBody UpdateClientRequest request) {
        ClientResponse response = clientService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idClient}", produces = "application/json")
    public @ResponseBody ResponseEntity<ClientResponse> delete(@PathVariable(value = "idClient") Long idClient) {
        ClientResponse response = clientService.delete(idClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
