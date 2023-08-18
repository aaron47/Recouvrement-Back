package com.example.recouvrement.controllers;

import com.example.recouvrement.mail.EmailDetails;
import com.example.recouvrement.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDetails emailDetails) {
        return ResponseEntity.ok(this.emailService.sendEmail(emailDetails));
    }
}
