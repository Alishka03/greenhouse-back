package kz.iitu.auth.repository;

import kz.iitu.auth.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {
    Optional<UserCredential> findByEmail(String email);
}
