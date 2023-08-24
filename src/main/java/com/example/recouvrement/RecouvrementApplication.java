package com.example.recouvrement;

import com.example.recouvrement.models.Client;
import com.example.recouvrement.models.CycleFacturation;
import com.example.recouvrement.models.Facture;
import com.example.recouvrement.models.TypeClient;
import com.example.recouvrement.models.helpers.Cycle;
import com.example.recouvrement.models.helpers.Type;
import com.example.recouvrement.repositories.ClientRepository;
import com.example.recouvrement.repositories.CycleFacturationRepository;
import com.example.recouvrement.repositories.FactureRepository;
import com.example.recouvrement.repositories.TypeClientRepository;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
            for (int i = 1; i <= 50; i++) {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String email = faker.internet().emailAddress();
                String phoneNumber = RandomStringUtils.randomNumeric(9); // Generate random 9-digit phone number
                String address = faker.address().fullAddress();
                String societe = faker.company().name();
                String numeroClient = RandomStringUtils.randomNumeric(9);
                String addresseDeFacturation = faker.address().fullAddress();


                clientRepository.save(new Client(null, firstName, lastName, email, phoneNumber, address, societe, numeroClient, addresseDeFacturation));
            }


            clientRepository.save(new Client(null, "John", "Doe", "johndoe@gmail.com", "123456789", "Sousse", "Billcount Consulting", "123456789", "Hammame Sousse"));
            clientRepository.save(new Client(null, "Jane", "Doe", "janedoe@gmail.com", "123456789", "Sousse", "Billcount Consulting", "1234567890", "Hammame Sousse"));
        };
    }

    @Bean
    CommandLineRunner run2(FactureRepository factureRepository, ClientRepository clientRepository) {
        return args -> {
            List<Client> clients = clientRepository.findAll();
            Random random = new Random();

            for (int i = 0; i < clients.size(); ++i) { // Generate 5 random factures
                // Create and save a facture with a random client
                Client randomClient = clients.get(random.nextInt(clients.size()));
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
    CommandLineRunner run3(TypeClientRepository typeClientRepository, ClientRepository clientRepository) {
        return args -> {
            List<Client> clients = clientRepository.findAll();
            List<Type> types = Arrays.asList(Type.values());
            Random random = new Random();

            for (Client client : clients) { // Generate 5 random factures
                TypeClient type = new TypeClient();
                type.setClient(client);
                type.setType(types.get(random.nextInt(types.size())));
                typeClientRepository.save(type);
            }
        };
    }

    @Bean
    CommandLineRunner run4(CycleFacturationRepository cycleFacturationRepository, ClientRepository clientRepository) {
        return args -> {
            List<Client> clients = clientRepository.findAll();
            List<Cycle> cycles = Arrays.asList(Cycle.values());
            Random random = new Random();

            for (Client client : clients) {
                CycleFacturation cycle = new CycleFacturation();
                cycle.setClient(client);
                cycle.setCycle(cycles.get(random.nextInt(cycles.size())));
                cycleFacturationRepository.save(cycle);
            }
        };
    }
}
