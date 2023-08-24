package com.example.recouvrement.services.client;

import com.example.recouvrement.dto.ClientDTO;
import com.example.recouvrement.dtomapper.ClientDTOMapper;
import com.example.recouvrement.exceptions.ClientNotFoundException;
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
    public ClientDTO findClientById(Long id) {
        Client client = this.clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client with id " + id + " not found"));
        return ClientDTOMapper.fromClient(client);
    }

    @Override
    public List<ClientDTO> findAllClients() {
        List<Client> clients = this.clientRepository.findAll();
        return ClientDTOMapper.fromClients(clients);
    }
}
