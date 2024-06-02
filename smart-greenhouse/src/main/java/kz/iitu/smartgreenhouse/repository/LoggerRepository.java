package kz.iitu.smartgreenhouse.repository;

import kz.iitu.smartgreenhouse.model.LoggerEntity;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface LoggerRepository extends ElasticsearchRepository<LoggerEntity,String >{
    @Query("{\"bool\": {\"must\": [{\"match\": {\"arduinoId\": \"?0\"}}, {\"range\": {\"LoggerELK.dateTime\": {\"gte\": \"?1\", \"lt\": \"?2\"}}}]}}")
    Iterable<LoggerEntity> findByArduinoIdAndDateTimeBetween(String arduinoId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"arduinoId\": \"?0\"}}, {\"range\": {\"dateTime\": {\"gte\": \"?1\", \"lte\": \"?2\"}}}]}}")
    List<LoggerEntity> findByArduinoIdAndDateTimeBetweenTwoDate(String arduinoId, LocalDateTime startDate, LocalDateTime endDate);

}

