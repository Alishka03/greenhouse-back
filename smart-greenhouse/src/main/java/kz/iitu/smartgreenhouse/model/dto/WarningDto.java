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

    public String generateWarningMessage() {
        StringBuilder warningMessage = new StringBuilder("Warnings with:");

        if (optimalTemperature != null && !optimalTemperature) {
            warningMessage.append(" temperature,");
        }
        if (optimalHumidity != null && !optimalHumidity) {
            warningMessage.append(" humidity,");
        }
        if (optimalLight != null && !optimalLight) {
            warningMessage.append(" light,");
        }
        if (optimalCarbonDioxide != null && !optimalCarbonDioxide) {
            warningMessage.append(" carbon dioxide,");
        }

        if (warningMessage.charAt(warningMessage.length() - 1) == ',') {
            warningMessage.deleteCharAt(warningMessage.length() - 1);
        }

        return warningMessage.toString();
    }

}