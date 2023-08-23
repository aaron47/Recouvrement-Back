package com.example.recouvrement.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
            SELECT t FROM Token t
            INNER JOIN t.client c
            WHERE c.id = :clientId AND (t.expired = false OR t.revoked = false)
                            """)
    List<Token> findAllValidTokensByClient(Long clientId);

    Optional<Token> findByToken(String token);
}
