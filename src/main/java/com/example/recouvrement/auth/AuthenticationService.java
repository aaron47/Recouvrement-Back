package com.example.recouvrement.auth;

import com.example.recouvrement.exceptions.ClientNotFoundException;
import com.example.recouvrement.config.JwtService;
import com.example.recouvrement.models.Client;
import com.example.recouvrement.repositories.ClientRepository;
import com.example.recouvrement.token.Token;
import com.example.recouvrement.token.TokenRepository;
import com.example.recouvrement.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ClientRepository clientRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        var client = this.clientRepository
                .findClientByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new ClientNotFoundException("Client not found with that email"));

        var jwt = this.jwtService.generateTokenWithoutClaims(client);
        this.revokeAllPreviousValidTokens(client);
        this.saveTokenToDb(client, jwt);
        logger.debug("Client found: {}", client);

        return AuthenticationResponse
                .builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .email(client.getEmail())
                .token(jwt)
                .build();
    }

    private void saveTokenToDb(Client client, String jwtToken) {
        Token token = Token
                .builder()
                .client(client)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        this.tokenRepository.save(token);
    }

    private void revokeAllPreviousValidTokens(Client client) {
        List<Token> validTokens = this.tokenRepository.findAllValidTokensByClient(client.getId());

        if (validTokens.isEmpty()) return;

        validTokens.forEach(token -> {
            System.out.println("TOKEN: " + token);
            token.setRevoked(true);
            token.setExpired(true);
        });

        tokenRepository.saveAll(validTokens);
    }
}
