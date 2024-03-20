package kz.iitu.auth.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoDto {

    private String firstName;
    private String lastName;
}
