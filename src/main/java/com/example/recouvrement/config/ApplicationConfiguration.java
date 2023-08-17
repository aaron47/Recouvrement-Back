package com.example.recouvrement.config;

import com.example.recouvrement.ClientNotFoundException;
import com.example.recouvrement.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final ClientRepository clientRepository;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> this.clientRepository
                .findClientByEmail(username)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with that email"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        logger.debug("UserDetailsService: {}", userDetailsService());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
