package kz.iitu.smartgreenhouse.controllers;

import kz.iitu.smartgreenhouse.model.LoggerEntity;
import kz.iitu.smartgreenhouse.model.dto.AnalyticResponseDTO;
import kz.iitu.smartgreenhouse.model.dto.LoggerSearchRequestDTO;
import kz.iitu.smartgreenhouse.model.wrapper.CustomResponse;
import kz.iitu.smartgreenhouse.service.LoggerService;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/logger")
public class LoggerController {
    private final LoggerService loggerService;

    public LoggerController(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @GetMapping
    public CustomResponse<?> getAll(){
        return loggerService.getAll();
    }

    @GetMapping("/arduino/{arduinoId}/average-values")
    public ResponseEntity<CustomResponse<?>> getAverageValuesForOneDay(
            @PathVariable String arduinoId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        CustomResponse<?> response = loggerService.getAverageValuesForOneDay(arduinoId, date.atStartOfDay());

        if (response.getSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/arduino/{arduinoId}/weekly-averages")
    public ResponseEntity<AnalyticResponseDTO> getWeeklyAverages(@PathVariable String arduinoId) {
        AnalyticResponseDTO analyticResponseDTO = loggerService.getWeeklyAverages(arduinoId);
        return ResponseEntity.ok(analyticResponseDTO);
    }


}
