package com.example.recouvrement.repositories;

import com.example.recouvrement.models.TypeClient;
import com.example.recouvrement.models.helpers.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeClientRepository extends JpaRepository<TypeClient, Long> {
    TypeClient findByType(Type type);

    TypeClient findByClientId(Long id);
}
