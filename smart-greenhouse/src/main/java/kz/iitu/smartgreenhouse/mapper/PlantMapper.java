package kz.iitu.smartgreenhouse.mapper;

import kz.iitu.smartgreenhouse.model.Plant;
import kz.iitu.smartgreenhouse.model.dto.PlantDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlantMapper extends EntityMapper<PlantDto, Plant> {

}
