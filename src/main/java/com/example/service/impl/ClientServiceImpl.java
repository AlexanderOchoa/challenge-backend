package com.example.service.impl;

import com.example.controller.request.SaveClientRequest;
import com.example.controller.request.UpdateClientRequest;
import com.example.controller.response.ClientResponse;
import com.example.entity.Client;
import com.example.exception.CustomErrorException;
import com.example.repository.ClientRepository;
import com.example.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientResponse register(SaveClientRequest request) {
        Client clientEmailInDb = clientRepository.findByEmail(request.getEmail());

        if (clientEmailInDb != null) {
            throw new CustomErrorException("The email of client exist.");
        }

        Client clientToSave = getClient(request);

        Client registeredClient = clientRepository.save(clientToSave);

        return getGetUserResponse(registeredClient);
    }

    @Override
    public List<ClientResponse> list() {
        List<Client> clientInDb = clientRepository.findAll();

        return clientInDb.stream()
                .map(this::getGetUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponse get(Long idClient) {
        Optional<Client> clientInDb = clientRepository.findById(idClient);

        if (!clientInDb.isPresent()) {
            throw new CustomErrorException("The client don't exist.");
        }

        return getGetUserResponse(clientInDb.get());
    }

    @Override
    public ClientResponse update(UpdateClientRequest request) {
        Optional<Client> clientInDb = clientRepository.findById(request.getIdClient());

        if (!clientInDb.isPresent()) {
            throw new CustomErrorException("The client don't exist.");
        }

        Client clientEmailInDb = clientRepository.findByEmail(request.getEmail());

        if (clientEmailInDb != null && !clientEmailInDb.getIdClient().equals(request.getIdClient())) {
            throw new CustomErrorException("The email of the client exist.");
        }

        Client clientToSave = getUpdateClient(request, clientInDb.get());

        Client updatedClient = clientRepository.save(clientToSave);

        return getGetUserResponse(updatedClient);
    }

    @Override
    public ClientResponse delete(Long idClient) {
        Optional<Client> clientInDb = clientRepository.findById(idClient);

        if (!clientInDb.isPresent()) {
            throw new CustomErrorException("The client don't exist.");
        }

        clientInDb.get().setState(0);
        Client clientUpdated = clientRepository.save(clientInDb.get());

        return getGetUserResponse(clientUpdated);
    }

    private Client getClient(SaveClientRequest request) {
        Client client = new Client();
        BeanUtils.copyProperties(request, client);
        client.setState(1);

        return client;
    }

    private ClientResponse getGetUserResponse(Client client) {
        ClientResponse response = new ClientResponse();
        BeanUtils.copyProperties(client, response);
        return response;
    }

    private Client getUpdateClient(UpdateClientRequest request, Client client) {
        BeanUtils.copyProperties(request, client);
        return client;
    }

}
