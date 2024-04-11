package kz.iitu.smartgreenhouse.model.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class WarningDto {
    private Boolean optimalTemperature;
    private Boolean optimalHumidityAir;
    private Boolean optimalHumidityGround;
    private Boolean optimalLight;
    private Boolean optimalCarbonDioxide;

    public String generateWarningMessage() {
        StringBuilder warningMessage = new StringBuilder("Warnings with:");

        if (optimalTemperature != null && !optimalTemperature) {
            warningMessage.append(" temperature,");
        }
        if (optimalHumidityAir != null && !optimalHumidityAir) {
            warningMessage.append(" humidity air,");
        }
        if (optimalHumidityAir != null && !optimalHumidityAir) {
            warningMessage.append(" humidity ground,");
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