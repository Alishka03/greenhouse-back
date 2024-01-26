package kz.iitu.auth.repository;

import kz.iitu.auth.entity.ResetPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordEntityRepository extends JpaRepository<ResetPasswordEntity,Long> {
}
