package com.example.recouvrement.services.facture;

import com.example.recouvrement.models.Facture;

import java.util.List;
import java.util.Map;

public interface IFactureService {
    Facture findFactureById(Long id);

    List<Facture> findAllFactures();

    List<Facture> findFacturesByClientId(Long id);

    Map<String, Double> getFactureStatistics();
}
