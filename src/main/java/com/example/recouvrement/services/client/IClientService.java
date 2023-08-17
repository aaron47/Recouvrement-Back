package com.example.recouvrement.services.client;

import com.example.recouvrement.models.Client;

import java.util.List;

public interface IClientService {
    Client findClientById(Long id);

    List<Client> findAllClients();
}
