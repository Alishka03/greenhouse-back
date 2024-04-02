package kz.iitu.smartgreenhouse.repository;

import kz.iitu.smartgreenhouse.model.LoggerEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;



public interface LoggerRepository extends ElasticsearchRepository<LoggerEntity,String >{

}

