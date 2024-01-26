package kz.iitu.auth.service;

import kz.iitu.auth.dto.AuthResponse;
import kz.iitu.auth.entity.UserCredential;
import kz.iitu.auth.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse saveUser(UserCredential credential) throws Exception {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        Optional<UserCredential> user = repository.findByEmail(credential.getEmail());
        if (user.isPresent()) {
            return null;
        }
        repository.save(credential);
        return AuthResponse.builder()
                .token(jwtService.generateToken(credential.getEmail()))
                .build();

    }

    public AuthResponse generateToken(String username) {
        return AuthResponse.builder()
                .userId(Objects.requireNonNull(repository.findByEmail(username).orElse(null)).getId())
                .token(jwtService.generateToken(username))
                .build();
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
