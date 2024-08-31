package com.jaiveer.backend.auth;

import com.jaiveer.backend.auth.dto.request.LoginRequest;
import com.jaiveer.backend.auth.dto.request.RegisterRequest;
import com.jaiveer.backend.auth.dto.response.AuthenticationResponse;
import com.jaiveer.backend.auth.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;


    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody RegisterRequest request
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }


    @PutMapping("/changeCredentials")
    public ResponseEntity<UserInfoResponse> changeCredentials(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.changeCredentials(request));
    }


    @GetMapping("/token")
    public ResponseEntity<UserInfoResponse> userInfo(
            @RequestHeader(name = "Authorization") String token
    ) {
        String jwt = token.substring(7);
        return ResponseEntity.ok(service.getUserInfoFromToken(jwt));
    }
}
