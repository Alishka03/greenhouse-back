package kz.iitu.smartgreenhouse.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoggerSearchRequestDTO {
    private String arduinoId;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private int pageNumber;
    private int pageSize;


}
