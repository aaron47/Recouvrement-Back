package com.example.recouvrement;

import com.example.recouvrement.models.Client;
import com.example.recouvrement.models.Facture;
import com.example.recouvrement.repositories.ClientRepository;
import com.example.recouvrement.repositories.FactureRepository;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class RecouvrementApplication {

    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        SpringApplication.run(RecouvrementApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ClientRepository clientRepository) {
        return args -> {
            // Generate 10 more clients with random names and emails
            for (int i = 1; i <= 10; i++) {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String email = faker.internet().emailAddress();
                String phoneNumber = RandomStringUtils.randomNumeric(9); // Generate random 9-digit phone number

                clientRepository.save(new Client(null, firstName, lastName, email, phoneNumber));
            }


            clientRepository.save(new Client(null, "John", "Doe", "johndoe@gmail.com", "123456789"));
            clientRepository.save(new Client(null, "Jane", "Doe", "janedoe@gmail.com", "123456789"));
        };
    }

    @Bean
    CommandLineRunner run2(FactureRepository factureRepository, ClientRepository clientRepository) {
        return args -> {
            List<Client> clients = clientRepository.findAll();
            Random random = new Random();

            for (int i = 0; i < 20; i++) { // Generate 5 random factures
                Client randomClient = clients.get(random.nextInt(clients.size())); // Get a random client

                // Create and save a facture with a random client
                Facture facture = new Facture();
                facture.setClient(randomClient);
                facture.setDescription(faker.lorem().sentence());
                facture.setMontant(random.nextDouble(1000) + 1);
                facture.setDateFacture(LocalDate.now());
                facture.setDateEcheance(LocalDate.now().plusDays(random.nextInt(30) + 1));
                // Set other attributes of the facture like date, amount, etc.
                factureRepository.save(facture);
            }
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        corsConfig.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization", "Jwt-Token", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfig.setExposedHeaders(
                Arrays.asList(
                        "Origin",
                        "Content-Type",
                        "Accept",
                        "Jwt-Token",
                        "Authorization",
                        "Access-Control-Allow-Origin",
                        "Access-Control-Allow-Origin",
                        "Access-Control-Allow-Credentials",
                        "Filename"
                )
        );
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(source);
    }
}
