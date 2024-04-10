package kz.iitu.smartgreenhouse.mapper;

import kz.iitu.smartgreenhouse.model.Arduino;
import kz.iitu.smartgreenhouse.model.dto.ArduinoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",uses = {PlantMapper.class})
public interface ArduinoMapper extends EntityMapper<ArduinoDto, Arduino> {
    @Mapping(target = "temperature" ,source = "temperature")
    @Mapping(target = "humidity" ,source = "humidity")
    @Mapping(target = "carbonDioxide" ,source = "carbonDioxide")
    @Mapping(target = "light" ,source = "light")
    @Mapping(target = "plant" ,source = "plant")
    ArduinoDto toDto(Arduino entity);

    @Named("base")
    @Mapping(target = "temperature" ,source = "temperature")
    @Mapping(target = "humidity" ,source = "humidity")
    @Mapping(target = "carbonDioxide" ,source = "carbonDioxide")
    @Mapping(target = "light" ,source = "light")
    @Mapping(target = "plant" ,source = "plant")
    ArduinoDto base(Arduino arduino);
}
