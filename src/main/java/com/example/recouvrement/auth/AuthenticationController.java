package com.example.recouvrement.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.recouvrement.models.helpers.Response;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("info", this.authenticationService.authenticate(authenticationRequest)))
                .message("Login successful")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @GetMapping("/me")
    public ResponseEntity<Response> isUserAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var isAuthenticated = authentication.isAuthenticated();

        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("isAuthenticated", isAuthenticated))
                .message("Client authenticated")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }
}
