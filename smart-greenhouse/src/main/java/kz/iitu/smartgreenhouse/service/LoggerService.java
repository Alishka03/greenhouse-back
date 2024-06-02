package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.model.LoggerEntity;
import kz.iitu.smartgreenhouse.model.dto.AnalyticResponseDTO;
import kz.iitu.smartgreenhouse.model.dto.Averages;
import kz.iitu.smartgreenhouse.model.wrapper.CustomResponse;
import kz.iitu.smartgreenhouse.repository.LoggerRepository;
import lombok.NonNull;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class LoggerService {
    private final LoggerRepository loggerRepository;

    @NonNull
    final ElasticsearchOperations esTemplate;


    public LoggerService(LoggerRepository loggerRepository, ElasticsearchOperations esTemplate) {
        this.loggerRepository = loggerRepository;
        this.esTemplate = esTemplate;
    }
    public CustomResponse<?> getAll() {
        CustomResponse<Iterable<LoggerEntity>> response = new CustomResponse<>();
        return response;
    }
    public CustomResponse<?> getAverageValuesForOneDay(String arduinoId, LocalDateTime date) {
        CustomResponse<Averages> response = new CustomResponse<>();
        response.setSuccess(true);
        try {
            LocalDateTime startDateTime = date.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endDateTime = date.withHour(23).withMinute(59).withSecond(59);

            Iterable<LoggerEntity> records = loggerRepository.findByArduinoIdAndDateTimeBetween(arduinoId, startDateTime, endDateTime);

            // Calculate average values
            float totalTemperature = 0;
            float totalCO2 = 0;
            float totalHumidityAir = 0;
            float totalHumidityGround = 0;
            float totalLight = 0;
            int count = 0;
            for (LoggerEntity record : records) {
                totalTemperature += record.getTemperature();
                totalCO2 += record.getCo2();
                totalHumidityAir += record.getHumidityAir();
                totalHumidityGround += record.getHumidityGround();
                totalLight += record.getLight();
                count++;
            }
            float avgTemperature = count > 0 ? totalTemperature / count : 0;
            float avgCO2 = count > 0 ? totalCO2 / count : 0;
            float avgHumidityAir = count > 0 ? totalHumidityAir / count : 0;
            float avgHumidityGround = count > 0 ? totalHumidityGround / count : 0;
            float avgLight = count > 0 ? totalLight / count : 0;

            Averages averages = new Averages(avgTemperature, avgCO2, avgHumidityAir, avgHumidityGround, avgLight);
            response.setData(averages);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setDesc(e.getMessage());
        }
        return response;
    }

    public AnalyticResponseDTO getWeeklyAverages(String arduinoId) {
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = endDateTime.minusWeeks(1);

        List<LoggerEntity> loggerEntities = loggerRepository.findByArduinoIdAndDateTimeBetweenTwoDate(arduinoId, startDateTime, endDateTime);

        List<AnalyticResponseDTO.AveragesPerDay> averagesPerDayList = new ArrayList<>();

        LocalDateTime currentDate = startDateTime;
        while (currentDate.isBefore(endDateTime.plusDays(1))) {
            List<LoggerEntity> dailyLogs = filterLogsByDate(loggerEntities, currentDate);
            AnalyticResponseDTO.AveragesPerDay averagesPerDay = calculateAveragesPerDay(currentDate, dailyLogs);
            averagesPerDayList.add(averagesPerDay);
            currentDate = currentDate.plusDays(1);
        }
        Collections.sort(averagesPerDayList, Comparator.comparing(AnalyticResponseDTO.AveragesPerDay::getDate));
        return new AnalyticResponseDTO(averagesPerDayList);
    }

    private List<LoggerEntity> filterLogsByDate(List<LoggerEntity> loggerEntities, LocalDateTime date) {
        List<LoggerEntity> filteredLogs = new ArrayList<>();
        LocalDateTime startOfDay = LocalDateTime.of(date.toLocalDate(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(date.toLocalDate(), LocalTime.MAX);
        for (LoggerEntity loggerEntity : loggerEntities) {
            if (loggerEntity.getDateTime().isAfter(startOfDay) && loggerEntity.getDateTime().isBefore(endOfDay)) {
                filteredLogs.add(loggerEntity);
            }
        }
        return filteredLogs;
    }

    private AnalyticResponseDTO.AveragesPerDay calculateAveragesPerDay(LocalDateTime date, List<LoggerEntity> logs) {
        float totalTemperature = 0;
        float totalCO2 = 0;
        float totalHumidityAir = 0;
        float totalHumidityGround = 0;
        float totalLight = 0;

        int numberOfLogs = logs.size();

        for (LoggerEntity log : logs) {
            totalTemperature += log.getTemperature();
            totalCO2 += log.getCo2();
            if (log.getHumidityAir() != null) {
                totalHumidityAir += log.getHumidityAir();
            }
            if (log.getHumidityGround() != null) {
                totalHumidityGround += log.getHumidityGround();
            }
            totalLight += log.getLight();
        }

        float avgTemperature = numberOfLogs > 0 ? totalTemperature / numberOfLogs : 0;
        float avgCO2 = numberOfLogs > 0 ? totalCO2 / numberOfLogs : 0;
        float avgHumidityAir = numberOfLogs > 0 ? totalHumidityAir / numberOfLogs : 0;
        float avgHumidityGround = numberOfLogs > 0 ? totalHumidityGround / numberOfLogs : 0;
        float avgLight = numberOfLogs > 0 ? totalLight / numberOfLogs : 0;

        AnalyticResponseDTO.Averages averages = new AnalyticResponseDTO.Averages(avgTemperature, avgCO2, avgHumidityAir, avgHumidityGround, avgLight);

        return new AnalyticResponseDTO.AveragesPerDay(date, averages);
    }





}
