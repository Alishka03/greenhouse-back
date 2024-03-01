package kz.iitu.auth.service;

import jakarta.transaction.Transactional;
import kz.iitu.auth.entity.UserCredential;
import kz.iitu.auth.errors.BadRequestError;
import kz.iitu.auth.repository.UserCredentialRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    private final UserCredentialRepository userCredentialRepository;

    public UserUtils(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }
    @Transactional
    public UserCredential getCurrentUser(){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        UserCredential userCredential =(UserCredential) usernamePasswordAuthenticationToken.getPrincipal();
        return userCredentialRepository.findByEmail(userCredential.getEmail()).orElseThrow(()->new BadRequestError("UserNotFound "));
    }
}
