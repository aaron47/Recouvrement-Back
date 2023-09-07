package com.example.recouvrement.controllers;

import com.example.recouvrement.dto.ClientDTO;
import com.example.recouvrement.models.Client;
import com.example.recouvrement.models.helpers.Response;
import com.example.recouvrement.services.client.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final IClientService clientService;

    @GetMapping("/all")
    public ResponseEntity<Response> findAllClients() {
        List<ClientDTO> clients = clientService.findAllClients();

        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("clients", clients))
                .message("Employees fetched sucessfully")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping("/statistics/role")
    public ResponseEntity<Response> getClientStatisticsByRole() {
        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("statistics", this.clientService.getClientStatisticsByType()))
                .message("Role statistics fetched successfully")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping("/statistics/cycle")
    public ResponseEntity<Response> getClientStatisticsByCycle() {
        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("statistics", this.clientService.getClientStatisticsByCycleDeFacturation()))
                .message("Cycle statistics fetched successfully")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }
}

