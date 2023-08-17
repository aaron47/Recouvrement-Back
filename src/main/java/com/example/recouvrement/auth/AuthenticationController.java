package com.example.recouvrement.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.recouvrement.models.helpers.Response;
import java.time.LocalDateTime;
import java.util.Map;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(Response
                .builder()
                .timestamp(LocalDateTime.now())
                .data(Map.of("info", this.authenticationService.authenticate(authenticationRequest)))
                .message("Login successful")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }
}
