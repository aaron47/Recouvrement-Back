package com.example.recouvrement.services.client;

import com.example.recouvrement.dto.ClientDTO;
import com.example.recouvrement.dtomapper.ClientDTOMapper;
import com.example.recouvrement.exceptions.ClientNotFoundException;
import com.example.recouvrement.models.Client;
import com.example.recouvrement.models.helpers.Cycle;
import com.example.recouvrement.models.helpers.Type;
import com.example.recouvrement.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Double> getClientStatisticsByType() {
        var allClients = this.clientRepository.findAll();

        int totalClients = allClients.size();
        var countClientsPersonne = allClients.stream().filter(client -> client.getType().equals(Type.PERSONNE)).count();
        var countClientsSociete = allClients.stream().filter(client -> client.getType().equals(Type.SOCIETE)).count();

        double percentClientsPersonne = (double) countClientsPersonne / totalClients * 100;
        double percentClientsSociete = (double) countClientsSociete / totalClients * 100;

        Map<String, Double> statistics = new HashMap<>();
        statistics.put("personne", percentClientsPersonne);
        statistics.put("societe", percentClientsSociete);

        return statistics;
    }

    @Override
    public Map<String, Double> getClientStatisticsByCycleDeFacturation() {
        var allClients = this.clientRepository.findAll();
        int totalClients = allClients.size();

        long countClientsMensuel = allClients.stream()
                .filter(client -> client.getCycle().equals(Cycle.MENSUEL))
                .count();

        long countClientsTrimestriel = allClients.stream()
                .filter(client -> client.getCycle().equals(Cycle.TRIMESTRIEL))
                .count();

        long countClientsSemestriel = allClients.stream()
                .filter(client -> client.getCycle().equals(Cycle.SEMESTRIEL))
                .count();

        long countClientsAnnuel = allClients.stream()
                .filter(client -> client.getCycle().equals(Cycle.ANNUEL))
                .count();

        double percentClientsMensuel = (double) countClientsMensuel / totalClients * 100;
        double percentClientsTrimestriel = (double) countClientsTrimestriel / totalClients * 100;
        double percentClientsSemestriel = (double) countClientsSemestriel / totalClients * 100;
        double percentClientsAnnuel = (double) countClientsAnnuel / totalClients * 100;

        Map<String, Double> statistics = new HashMap<>();
        statistics.put("mensuel", percentClientsMensuel);
        statistics.put("trimestriel", percentClientsTrimestriel);
        statistics.put("semestriel", percentClientsSemestriel);
        statistics.put("annuel", percentClientsAnnuel);

        return statistics;
    }

}
