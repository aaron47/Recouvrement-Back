package com.example.recouvrement.services.client;

import com.example.recouvrement.dto.ClientDTO;

import java.util.List;
import java.util.Map;

public interface IClientService {
    ClientDTO findClientById(Long id);

    List<ClientDTO> findAllClients();

    Map<String, Double> getClientStatisticsByType();

    Map<String, Double> getClientStatisticsByCycleDeFacturation();
}
