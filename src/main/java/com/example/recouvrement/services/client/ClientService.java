package com.example.recouvrement.services.client;

import com.example.recouvrement.ClientNotFoundException;
import com.example.recouvrement.models.Client;
import com.example.recouvrement.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client findClientById(Long id) {
        return this.clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client with id " + id + " not found"));
    }

    @Override
    public List<Client> findAllClients() {
        return this.clientRepository.findAll();
    }
}
