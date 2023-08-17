package com.example.recouvrement.services.facture;

import com.example.recouvrement.models.Facture;
import com.example.recouvrement.repositories.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FactureService implements IFactureService {

    private final FactureRepository factureRepository;

    @Override
    public Facture findFactureById(Long id) {
        return this.factureRepository.findFactureById(id);
    }

    @Override
    public List<Facture> findAllFactures() {
        return this.factureRepository.findAll().stream().toList();
    }

    @Override
    public List<Facture> findFacturesByClientId(Long id) {
        return this.factureRepository.findFacturesByClientId(id);
    }
}
