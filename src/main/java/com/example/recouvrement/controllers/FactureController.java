package com.example.recouvrement.controllers;

import com.example.recouvrement.models.Facture;
import com.example.recouvrement.models.helpers.Response;
import com.example.recouvrement.services.facture.IFactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/factures")
@RequiredArgsConstructor
public class FactureController {
    private final IFactureService factureService;

    @GetMapping("/all")
    public ResponseEntity<Response> findAllFactures() {
        List<Facture> factures = factureService.findAllFactures();

        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("factures", factures))
                .message("Factures fetched sucessfully")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Response> findFacturesByClientId(@PathVariable("id") Long id) {
        List<Facture> factures = factureService.findFacturesByClientId(id);

        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("factures", factures))
                .message("Factures fetched sucessfully")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findFactureById(@PathVariable("id") Long id) {
        Facture facture = factureService.findFactureById(id);

        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("facture", facture))
                .message("Facture fetched sucessfully")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }
}
