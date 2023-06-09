package com.jaiveer.backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws Exception {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }

    @GetMapping("/token")
    public UserInfoResponse getUserInfo(
            @RequestHeader(name = "Authorization") String token
    ) {
        String jwt = token.substring(7);
        return new UserInfoResponse(service.getId(jwt), service.getFirstName(jwt), service.getLastName(jwt), service.getEmail(jwt));
    }
}
