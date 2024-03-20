package kz.iitu.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class LoginError {
    private int status;
    private String error;
    private LocalDateTime timestamp;
}
