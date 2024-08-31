package com.jaiveer.backend.auth;

import com.jaiveer.backend.auth.dto.request.LoginRequest;
import com.jaiveer.backend.auth.dto.request.RegisterRequest;
import com.jaiveer.backend.auth.dto.response.AuthenticationResponse;
import com.jaiveer.backend.auth.dto.response.UserInfoResponse;
import com.jaiveer.backend.config.JwtService;
import com.jaiveer.backend.user.User;
import com.jaiveer.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(RegisterRequest request) throws Exception {
        request.validateUserDetails();


        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new Exception("User with this email already exists");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(hashedPassword)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails user = (UserDetails) auth.getPrincipal();

        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public String getEmail(String token) {
        return jwtService.extractUsername(token);
    }

    public String getFirstName(String token) {
        User s = getUserFromToken(token);
        return s.getFirstname();
    }

    public String getLastName(String token) {
        User s = getUserFromToken(token);
        return s.getLastname();
    }

    public String getId(String token) {
        User s = getUserFromToken(token);
        return s.getId().toString();
    }

    public User getUserFromToken(String token) {
        String email = getEmail(token);
        return userRepository.findByEmailIgnoreCase(email);
    }

    public UserInfoResponse getUserInfoFromToken(String token) {
        String email = getEmail(token);
        User user = userRepository.findByEmailIgnoreCase(email);
        return new UserInfoResponse(user);
    }

    public UserInfoResponse changeCredentials(RegisterRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return new UserInfoResponse(userRepository.save(user));
    }
}
