package com.example.recouvrement.repositories;

import com.example.recouvrement.models.Email;
import com.example.recouvrement.models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Email findByFacture(Facture facture);

    Optional<Email> findByRecipient(String recipient);
}
