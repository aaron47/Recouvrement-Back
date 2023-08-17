package com.example.recouvrement.repositories;

import com.example.recouvrement.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientById(Long id);
}
