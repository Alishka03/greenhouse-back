package kz.iitu.smartgreenhouse.model.dto;

import kz.iitu.smartgreenhouse.model.Arduino;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class InsertResponseDto {
    private Arduino arduino;
    private WarningDto warningDto;
}
