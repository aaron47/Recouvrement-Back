package com.example.recouvrement.repositories;

import com.example.recouvrement.models.Client;
import com.example.recouvrement.models.CycleFacturation;
import com.example.recouvrement.models.helpers.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CycleFacturationRepository extends JpaRepository<CycleFacturation, Long> {
    CycleFacturation findByClient(Client client);

    List<CycleFacturation> findCycleFacturationsByCycle(Cycle cycle);
}
