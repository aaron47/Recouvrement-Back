package com.example.recouvrement.auth;

import com.example.recouvrement.ClientNotFoundException;
import com.example.recouvrement.config.JwtService;
import com.example.recouvrement.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
       var test = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        System.out.println(test);

        var client = this.clientRepository.findClientByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new ClientNotFoundException("Client not found with that email"));
        var jwt = this.jwtService.generateTokenWithoutClaims(client);
        logger.debug("Client found: {}", client);


        return AuthenticationResponse.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .email(client.getEmail())
                .token(jwt)
                .build();
    }
}
