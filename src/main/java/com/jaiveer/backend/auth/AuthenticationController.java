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

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(
            @RequestBody RegisterRequest request
    ) {
        try {
            
            AuthenticationResponse registeredResponse = authenticationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredResponse);

        } catch (Exception e) {

            Map<String, String> errorMessage = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);

        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }


    @PutMapping("/changeCredentials")
    public ResponseEntity<UserInfoResponse> changeUserCredentials(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.changeCredentials(request));
    }


    @GetMapping("/token")
    public ResponseEntity<UserInfoResponse> getUserInformation(
            @RequestHeader(name = "Authorization") String token
    ) {
        int tokenPrefixLength = 7;
        String jwtToken = token.substring(tokenPrefixLength);
        return ResponseEntity.ok(authenticationService.getUserInfoFromToken(jwtToken));
    }
}
