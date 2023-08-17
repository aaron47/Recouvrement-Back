package com.example.recouvrement.repositories;

import com.example.recouvrement.models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    Facture findFactureById(Long id);

    List<Facture> findFacturesByClientId(Long id);
}
