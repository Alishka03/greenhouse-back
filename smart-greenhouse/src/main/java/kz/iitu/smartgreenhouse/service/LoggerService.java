package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.model.LoggerEntity;
import kz.iitu.smartgreenhouse.model.wrapper.CustomResponse;
import kz.iitu.smartgreenhouse.repository.LoggerRepository;
import lombok.NonNull;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

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
        response.setSuccess(true);
        try {
            response.setData(loggerRepository.findAll());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

}
