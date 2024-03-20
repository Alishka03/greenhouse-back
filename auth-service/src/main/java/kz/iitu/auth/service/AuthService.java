package kz.iitu.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import kz.iitu.auth.dto.AuthResponse;
import kz.iitu.auth.dto.ProfileInfoDto;
import kz.iitu.auth.dto.RequestResponseDto;
import kz.iitu.auth.entity.ResetPasswordEntity;
import kz.iitu.auth.entity.UserCredential;
import kz.iitu.auth.errors.BadRequestError;
import kz.iitu.auth.errors.ForbiddenError;
import kz.iitu.auth.errors.ValidationError;
import kz.iitu.auth.errors.ValidationResult;
import kz.iitu.auth.repository.ResetPasswordEntityRepository;
import kz.iitu.auth.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public AuthResponse saveUser(UserCredential credential) throws Exception {

        Optional<UserCredential> user = repository.findByEmail(credential.getEmail());
        if (user.isPresent()) {
            throw new ForbiddenError("User exists with email : " + credential.getEmail());
        }
        ValidationResult<Map<String, Object>> validationResult = registrationValidationResult(credential);
        if (validationResult.isHasError()) {
            throw new ValidationError(validationResult.getBody());
        }
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        UserCredential userToSave = repository.save(credential);
        return AuthResponse.builder()
                .token(jwtService.generateToken(credential.getEmail()))
                .userId(userToSave.getId())
                .lastName(userToSave.getLastname())
                .firstName(userToSave.getFirstname())
                .profilePicture(userToSave.getPicture())
                .build();

    }

    public AuthResponse generateToken(String username) {
        Optional<UserCredential> user = repository.findByEmail(username);
        if (user.isEmpty()) {
            throw new ForbiddenError("User not found with email : " + username);
        }
        return AuthResponse.builder()
                .userId(Objects.requireNonNull(repository.findByEmail(username).orElse(null)).getId())
                .lastName(user.get().getLastname())
                .firstName(user.get().getFirstname())
                .profilePicture(user.get().getPicture())
                .token(jwtService.generateToken(username))
                .build();
    }

    public RequestResponseDto getCodeViaEmail(String email) {
        Optional<UserCredential> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new BadRequestError("User not found with this email : " + email);
        }
        Long random4DigitLong = ThreadLocalRandom.current().nextLong(1000, 10000);
        Optional<ResetPasswordEntity> resetPassword = passwordEntityRepository.findById(user.get().getId());
        if (resetPassword.isEmpty()) {
            ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity(user.get().getId(), random4DigitLong);
            passwordEntityRepository.save(resetPasswordEntity);
        } else {
            ResetPasswordEntity resetPasswordEntity = resetPassword.get();
            resetPasswordEntity.setHashCode(random4DigitLong);
            passwordEntityRepository.save(resetPasswordEntity);
        }
        mailSender.sendMail(email, "GreenHouse App Team", "" +
                "                           Hello!\n" +
                "Did you forget the password? This is your code to reset password : " + random4DigitLong);
        Context context = new Context();
        context.setVariable("message", "some message");
//        mailSender.sendEmailWithHttpTemplate(email,"GREENHOUSE-RESET-PASSWORD","email-template",context);
        return new RequestResponseDto("Reset code has sent succesfully", LocalDateTime.now());
    }

    public RequestResponseDto changePassword(String email, String password, Long code) {
        Optional<UserCredential> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new BadRequestError("User not found with this email : " + email);
        }
        UserCredential userToSave = user.get();
        Optional<ResetPasswordEntity> passwordEntity = passwordEntityRepository.findById(userToSave.getId());
        if (passwordEntity.isEmpty()) {
            throw new BadRequestError("HashCode not found for user : " + email);
        } else {
            if (Objects.equals(code, passwordEntity.get().getHashCode())) {
                System.out.println("EQUALS");
                userToSave.setPassword(passwordEncoder.encode(password));
            } else {
                throw new BadRequestError("HashCode is incorrect : " + email);
            }
        }
        repository.save(userToSave);
        return new RequestResponseDto("Password has changed successfully for :" + email, LocalDateTime.now());
    }


    public UserCredential getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.get("sub", String.class);
        return repository.findByEmail(username).orElseThrow(() -> new BadRequestError("Bad credentials"));
    }


    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public UserCredential updateProfileImage(String imageUrl, String token) {
        UserCredential user = getUserFromToken(token);
        user.setPicture(imageUrl);
        return repository.save(user);
    }

    public UserCredential updateProfileInfo(ProfileInfoDto profileInfo, String token) {
        UserCredential user = getUserFromToken(token);
        if(isNameInvalid(profileInfo.getFirstName())){
            throw new ValidationError("Заполните все поля");
        }
        if(isNameInvalid(profileInfo.getLastName())){
            throw new ValidationError("Заполните все поля");
        }

        user.setFirstname(profileInfo.getFirstName());
        user.setLastname(profileInfo.getLastName());
        return repository.save(user);
    }

    private ValidationResult<Map<String, Object>> registrationValidationResult(UserCredential userCredential) {
        ValidationResult<Map<String, Object>> validationResult = new ValidationResult<>();
        Map<String, Object> hashMapUser = new HashMap<>();
        if (isNameInvalid(userCredential.getFirstname())) {
            hashMapUser.put("firstName", "Поле обязательна к заполнению *");
            validationResult.setHasError(true);
        }
        if (isNameInvalid(userCredential.getLastname())) {
            hashMapUser.put("lastName", "Поле обязательна к заполнению *");
            validationResult.setHasError(true);
        }
        if (isNameInvalid(userCredential.getEmail())) {
            hashMapUser.put("email", "Поле обязательна к заполнению *");
            validationResult.setHasError(true);
        }
        if (isNameInvalid(userCredential.getPassword())) {
            hashMapUser.put("password", "Поле обязательна к заполнению *");
            validationResult.setHasError(true);
        }
        validationResult.setBody(hashMapUser);
        return validationResult;
    }

    private boolean isNameInvalid(String text) {
        return text == null || text.isEmpty();
    }
}
