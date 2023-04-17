package com.jaiveer.backend.auth;

import com.jaiveer.backend.config.JwtService;
import com.jaiveer.backend.user.Role;
import com.jaiveer.backend.user.User;
import com.jaiveer.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        request.validateAll();
        if (userRepo.existsByEmailIgnoreCase(request.getEmail())) {
            throw new Exception("user with this email already exists");
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByEmailIgnoreCase(request.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }


    public String getFirstName(String token) {
        String email = jwtService.extractUsername(token);
        User s = userRepo.findByEmailIgnoreCase(email);
        return s.getFirstname();
    }

    public String getId(String token) {
        String email = jwtService.extractUsername(token);
        User s = userRepo.findByEmailIgnoreCase(email);
        return s.getId().toString();
    }

}
