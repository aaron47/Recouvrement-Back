package com.example.recouvrement.repositories;

import com.example.recouvrement.models.Alert;
import com.example.recouvrement.models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findByFacture(Facture facture);
}
