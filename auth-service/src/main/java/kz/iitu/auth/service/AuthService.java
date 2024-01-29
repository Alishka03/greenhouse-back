package kz.iitu.auth.service;

import kz.iitu.auth.dto.AuthResponse;
import kz.iitu.auth.dto.RequestResponseDto;
import kz.iitu.auth.entity.ResetPasswordEntity;
import kz.iitu.auth.entity.UserCredential;
import kz.iitu.auth.repository.ResetPasswordEntityRepository;
import kz.iitu.auth.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ResetPasswordEntityRepository passwordEntityRepository;
    private final JwtService jwtService;
    private final SMTPService mailSender;

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

    public RequestResponseDto getCodeViaEmail(String email){
        Optional<UserCredential> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            return new RequestResponseDto("User not found with this email : " + email, LocalDateTime.now());
        }
        Long random4DigitLong = ThreadLocalRandom.current().nextLong(1000, 10000);
        Optional<ResetPasswordEntity> resetPassword = passwordEntityRepository.findById(user.get().getId());
        if(resetPassword.isEmpty()){
            ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity(user.get().getId(),random4DigitLong);
            passwordEntityRepository.save(resetPasswordEntity);
        }else{
            ResetPasswordEntity resetPasswordEntity = resetPassword.get();
            resetPasswordEntity.setHashCode(random4DigitLong);
            passwordEntityRepository.save(resetPasswordEntity);
        }
        mailSender.sendMail(email,"CODE TO RESET PASSWORD","This is your code to reset password : " + random4DigitLong);
        return new RequestResponseDto("Reset code has sent succesfully", LocalDateTime.now());
    }

    public RequestResponseDto changePassword(String email , String password , Long code){
        Optional<UserCredential> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            return new RequestResponseDto("User not found with this email : " + email, LocalDateTime.now());
        }
        UserCredential userToSave = user.get();
        Optional<ResetPasswordEntity> passwordEntity = passwordEntityRepository.findById(userToSave.getId());
        if(passwordEntity.isEmpty()){
            return new RequestResponseDto("HashCode not found for user : " + email, LocalDateTime.now());
        }else{
            if(Objects.equals(code, passwordEntity.get().getHashCode())){
                System.out.println("EQUALS");
                userToSave.setPassword(passwordEncoder.encode(password));
            }
        }
        repository.save(userToSave);
        return new RequestResponseDto("Password has changed successfully for :" + email , LocalDateTime.now());
    }


    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}