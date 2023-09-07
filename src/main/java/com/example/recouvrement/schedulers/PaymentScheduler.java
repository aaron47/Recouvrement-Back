package com.example.recouvrement.schedulers;

import com.example.recouvrement.mail.EmailDetails;
import com.example.recouvrement.mail.EmailService;
import com.example.recouvrement.models.Alert;
import com.example.recouvrement.models.Email;
import com.example.recouvrement.models.Facture;
import com.example.recouvrement.models.helpers.FactureStatus;
import com.example.recouvrement.repositories.AlertRepository;
import com.example.recouvrement.repositories.EmailRepository;
import com.example.recouvrement.repositories.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentScheduler {
    private final EmailService emailService;
    private final EmailRepository emailRepository;
    private final FactureRepository factureRepository;
    private final AlertRepository alertRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void checkFacturePaymentDate() {
        List<Facture> factureList = this.factureRepository.findAll();

        for (Facture facture : factureList) {
            LocalDate facturePaymentDate = facture.getDateEcheance();
            LocalDate currentDate = LocalDate.now();
            LocalDate reminderDate = facturePaymentDate.minusDays(3);
            long daysDifference = currentDate.toEpochDay() - facturePaymentDate.toEpochDay();

            if (daysDifference >= 4 && facture.getFactureStatus() == FactureStatus.IMPAYEE) {
                Alert alert = new Alert();
                alert.setFacture(facture);
                this.alertRepository.save(alert);
                this.sendEmail(facture);
            }

            if (currentDate.equals(reminderDate) && facture.getFactureStatus() == FactureStatus.IMPAYEE) {
                this.sendEmail(facture);
            }
        }
    }


    private void sendEmail(Facture facture) {
        EmailDetails emailDetails = new EmailDetails(
                facture.getClient().getEmail(),
                "Le paiement de votre facture avec l'id " + facture.getId() + " est dû dans 3 jours. Veuillez vous assurer de payer.",
                "Paiement de votre facture"
        );

        Email dbEmail = this.emailRepository.findByRecipient(emailDetails.getRecipient()).orElse(null);

        if (dbEmail != null) {
            return;
        }

        Email email = new Email(
                null,
                facture.getClient(),
                facture,
                emailDetails.getSubject(),
                emailDetails.getMsgBody(),
                emailDetails.getRecipient(),
                LocalDate.now()
        );

        this.emailService.sendEmail(emailDetails);
        this.emailRepository.save(email);
    }
}
