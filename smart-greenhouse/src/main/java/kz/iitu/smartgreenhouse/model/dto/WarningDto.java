package kz.iitu.smartgreenhouse.model.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class WarningDto {
    private Boolean optimalTemperature;
    private Boolean optimalHumidity;
    private Boolean optimalLight;
    private Boolean optimalCarbonDioxide;
}