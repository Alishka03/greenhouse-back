package kz.iitu.smartgreenhouse.mapper;

import kz.iitu.smartgreenhouse.model.Greenhouse;
import kz.iitu.smartgreenhouse.model.User;
import kz.iitu.smartgreenhouse.model.dto.GreenhouseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",uses = {ArduinoMapper.class,PlantMapper.class})
public interface GreenhouseMapper extends EntityMapper<GreenhouseDto, Greenhouse> {
    @Mapping(target = "id" ,source = "id")
    @Mapping(target = "owner" ,source = "owner",qualifiedByName = "userId")
    @Mapping(target = "name" ,source = "name")
    @Mapping(target = "arduino" ,source = "arduino",qualifiedByName = "base")
    GreenhouseDto toDto(Greenhouse greenhouse);


    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id" , source ="id")
    @Mapping(target = "firstname" , source ="firstname")
    @Mapping(target = "lastname" , source ="lastname")
    @Mapping(target = "email" , source ="email")
    @Mapping(target = "picture" , source ="picture")
    @Mapping(target = "password" , source ="password" , ignore = true)
    @Mapping(target = "greenhouses" , source ="greenhouses" , ignore = true)
    User userId(User user);

}
