package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.feign.AuthServiceFeign;
import kz.iitu.smartgreenhouse.mapper.ArduinoMapper;
import kz.iitu.smartgreenhouse.model.Arduino;
import kz.iitu.smartgreenhouse.model.criteria.ArduinoData;
import kz.iitu.smartgreenhouse.model.dto.ArduinoDto;
import kz.iitu.smartgreenhouse.model.criteria.ArduinoCriteria;
import kz.iitu.smartgreenhouse.model.dto.PageResponse;
import kz.iitu.smartgreenhouse.repository.ArduinoRepository;
import kz.iitu.smartgreenhouse.web.rest.errors.BadRequestError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArduinoService {
    private final ArduinoRepository arduinoRepository;

    private final ArduinoMapper arduinoMapper;

    private final AuthServiceFeign authServiceFeign;

    public ArduinoService(ArduinoRepository arduinoRepository, ArduinoMapper arduinoMapper, AuthServiceFeign authServiceFeign) {
        this.arduinoRepository = arduinoRepository;
        this.arduinoMapper = arduinoMapper;
        this.authServiceFeign = authServiceFeign;
    }

    public Arduino save(ArduinoDto arduinoDto) {
        Arduino arduino = arduinoMapper.toEntity(arduinoDto);
        arduino = arduinoRepository.save(arduino);
        return arduino;
    }

    public Optional<Arduino> partialUpdate(ArduinoDto arduinoDto) {
        return arduinoRepository
                .findById(arduinoDto.getId())
                .map(existingArduino -> {
                    arduinoMapper.partialUpdate(existingArduino, arduinoDto);
                    return existingArduino;
                })
                .map(arduinoRepository::save);
    }

    public PageResponse<Arduino> findAllPageable(ArduinoCriteria arduinoCriteria) {
        PageResponse<Arduino> response = new PageResponse<>();
        Page<Arduino> arduinoPage = arduinoRepository.findAll(
                PageRequest.of(
                        arduinoCriteria.getPage() > 0 ? arduinoCriteria.getPage() - 1 : 0,
                        arduinoCriteria.getSize()
                )
        );
        response.setTotalCount(arduinoPage.getTotalElements());
        response.setTotalPages(arduinoPage.getTotalPages());
        response.setItems(arduinoPage.getContent());
        return response;
    }
    public Optional<Arduino> findById(Long id) {
        return arduinoRepository.findById(id);
    }

    public Arduino insertData(ArduinoData data){
        Optional<Arduino> arduino = findById(data.getId());
        if(arduino.isEmpty()){
            throw new BadRequestError("Arduino not found with ID : "+data.getId());
        }
        Arduino entity = arduino.get();
        if(data.getCo2()!=null){
            entity.setCarbonDioxide(data.getCo2());
        }
        if(data.getHumidity()!=null){
            entity.setHumidity(data.getHumidity());
        }
        if(data.getTemperature()!=null){
            entity.setTemperature(data.getTemperature());
        }
        return arduinoRepository.save(entity);
    }

    public void deleteArduino(Long id) {

        arduinoRepository.deleteById(id);
    }
}
