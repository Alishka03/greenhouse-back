package kz.iitu.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_hashs")
public class ResetPasswordEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "hash_code")
    private Long hashCode;
}
