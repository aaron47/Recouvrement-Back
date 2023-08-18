package com.example.recouvrement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "factures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "facture_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private Double montant;

    private LocalDate dateFacture;

    private String description;

    private LocalDate dateEcheance;
}
