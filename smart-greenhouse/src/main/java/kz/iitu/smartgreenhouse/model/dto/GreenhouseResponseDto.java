package kz.iitu.smartgreenhouse.model.dto;

import kz.iitu.smartgreenhouse.model.Greenhouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class GreenhouseResponseDto {
    private GreenhouseDto greenhouse;
    private WarningDto warning;
}
