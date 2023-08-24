package com.example.recouvrement.dtomapper;

import com.example.recouvrement.dto.ClientDTO;
import com.example.recouvrement.models.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientDTOMapper {
    public static ClientDTO fromClient(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client, clientDTO);
        return clientDTO;
    }

    public static Client toClient(ClientDTO clientDTO) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        return client;
    }

    public static List<ClientDTO> fromClients(List<Client> client) {
        return client.stream().map(ClientDTOMapper::fromClient).toList();
    }
}
