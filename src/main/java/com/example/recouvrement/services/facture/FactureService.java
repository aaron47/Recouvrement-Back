package com.example.recouvrement.services.facture;

import com.example.recouvrement.models.Facture;
import com.example.recouvrement.models.helpers.FactureStatus;
import com.example.recouvrement.repositories.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Double> getFactureStatistics() {
        List<Facture> factures = this.factureRepository.findAll();

        long facturesPayee = factures.stream().filter(facture -> facture.getFactureStatus() == FactureStatus.PAYE).count();
        long facturesImpayee = factures.stream().filter(facture -> facture.getFactureStatus() == FactureStatus.IMPAYEE).count();

        double percentFacturesPayee = (double) facturesPayee / factures.size() * 100;
        double percentFacturesImpayee = (double) facturesImpayee / factures.size() * 100;

        Map<String, Double> statistics = new HashMap<>();
        statistics.put("payee", percentFacturesPayee);
        statistics.put("impayee", percentFacturesImpayee);

        return statistics;
    }
}
