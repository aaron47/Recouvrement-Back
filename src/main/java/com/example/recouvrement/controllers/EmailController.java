package com.example.recouvrement.controllers;

import com.example.recouvrement.mail.EmailDetails;
import com.example.recouvrement.mail.EmailService;
import com.example.recouvrement.models.helpers.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("send")
    public ResponseEntity<Response> sendEmail(@RequestBody EmailDetails emailDetails) {
        String response = emailService.sendEmail(emailDetails);
        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("response", response))
                .message("Employees fetched sucessfully")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }
}
