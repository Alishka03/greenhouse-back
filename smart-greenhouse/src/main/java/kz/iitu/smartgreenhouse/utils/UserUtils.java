package kz.iitu.smartgreenhouse.utils;

import kz.iitu.smartgreenhouse.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    private final UserRepository userCredentialRepository;

    public UserUtils(UserRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

}
