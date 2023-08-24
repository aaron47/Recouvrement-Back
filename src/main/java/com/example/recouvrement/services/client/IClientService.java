package com.example.recouvrement.services.client;

import com.example.recouvrement.dto.ClientDTO;
import com.example.recouvrement.models.Client;

import java.util.List;

public interface IClientService {
    ClientDTO findClientById(Long id);

    List<ClientDTO> findAllClients();
}
