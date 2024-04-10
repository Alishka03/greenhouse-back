package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.feign.AuthServiceFeign;
import kz.iitu.smartgreenhouse.mapper.ArduinoMapper;
import kz.iitu.smartgreenhouse.mapper.GreenhouseMapper;
import kz.iitu.smartgreenhouse.model.Arduino;
import kz.iitu.smartgreenhouse.model.Greenhouse;
import kz.iitu.smartgreenhouse.model.Plant;
import kz.iitu.smartgreenhouse.model.User;
import kz.iitu.smartgreenhouse.model.criteria.GreenhouseCriteria;
import kz.iitu.smartgreenhouse.model.dto.GreenhouseDto;
import kz.iitu.smartgreenhouse.model.dto.GreenhouseResponseDto;
import kz.iitu.smartgreenhouse.model.dto.PageResponse;
import kz.iitu.smartgreenhouse.model.dto.WarningDto;
import kz.iitu.smartgreenhouse.repository.ArduinoRepository;
import kz.iitu.smartgreenhouse.repository.GreenhouseRepository;
import kz.iitu.smartgreenhouse.web.rest.errors.BadRequestError;
import kz.iitu.smartgreenhouse.web.rest.errors.ObjectNotFoundError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GreenhouseService {
    private final GreenhouseRepository greenhouseRepository;
    private final AuthServiceFeign authServiceFeign;

    private final ArduinoMapper arduinoMapper;
    private final GreenhouseMapper mapper;


    private final ArduinoRepository arduinoRepository;
    public GreenhouseService(GreenhouseRepository greenhouseRepository, AuthServiceFeign authServiceFeign, ArduinoMapper arduinoMapper, GreenhouseMapper mapper,  ArduinoRepository arduinoRepository) {
        this.greenhouseRepository = greenhouseRepository;
        this.authServiceFeign = authServiceFeign;
        this.arduinoMapper = arduinoMapper;
        this.mapper = mapper;
        this.arduinoRepository = arduinoRepository;
    }

    public GreenhouseDto save(GreenhouseDto greenhouseDto,String token) {
        Greenhouse greenhouse = new Greenhouse();
        greenhouse.setName(greenhouseDto.getName());
        User currenUser = authServiceFeign.getCurrentUser(token);
        greenhouse.setOwner(currenUser);
        greenhouse.setArduino(arduinoMapper.toEntity(greenhouseDto.getArduino()));
        Greenhouse saved =  greenhouseRepository.save(greenhouse);
        return mapper.toDto(saved);
    }

    public Optional<GreenhouseDto> partialUpdate(GreenhouseDto greenhouseDto) {
        return greenhouseRepository
                .findById(greenhouseDto.getId())
                .map(existingGreenhouse -> {
                    if (greenhouseDto.getName() != null) {
                        existingGreenhouse.setName(greenhouseDto.getName());
                    }
                    if (greenhouseDto.getArduino() != null) {
                        existingGreenhouse.setArduino(arduinoRepository.findById(greenhouseDto.getArduino().getId()).orElseThrow(() -> new ObjectNotFoundError("Arduino not found with id: "+ greenhouseDto.getArduino().getId())));
                    }
                    return existingGreenhouse;
                })
                .map(greenhouseRepository::save)
                .map(mapper::toDto);
    }

    public PageResponse<GreenhouseDto> findAllPageable(GreenhouseCriteria greenhouseCriteria) {
        PageResponse<GreenhouseDto> response = new PageResponse<>();
        Page<Greenhouse> greenhousePage = greenhouseRepository.findAll(
                PageRequest.of(
                        greenhouseCriteria.getPage() > 0 ? greenhouseCriteria.getPage() - 1 : 0,
                        greenhouseCriteria.getSize()
                )
        );
        response.setTotalCount(greenhousePage.getTotalElements());
        response.setTotalPages(greenhousePage.getTotalPages());
        response.setItems(greenhousePage.getContent().stream().map(mapper::toDto).collect(Collectors.toList()));
        return response;
    }

    public Optional<GreenhouseDto> findById(Long id) {
        return greenhouseRepository.findById(id).map(mapper::toDto);
    }

    public Optional<GreenhouseDto> updateGreenhouse(GreenhouseDto dto, String bearerToken) {
        User user = authServiceFeign.getCurrentUser(bearerToken);
        List<Greenhouse> greenhouseList = greenhouseRepository.findAllByOwnerId(user.getId());
        Greenhouse givenGreenhouse = greenhouseRepository.findById(dto.getId())
                .orElseThrow(() -> new ObjectNotFoundError("Greenhouse not found with id: " + dto.getId()));
        if (!greenhouseList.contains(givenGreenhouse)) {
            throw new BadRequestError("You don't have permission to update this greenhouse");
        }
        return partialUpdate(dto);
    }

    public void deleteGreenhouse(Long id,String token){
        User user = authServiceFeign.getCurrentUser(token);
        List<Greenhouse> greenhouseList = greenhouseRepository.findAllByOwnerId(user.getId());
        Greenhouse givenGreenhouse = greenhouseRepository.findById(id).orElseThrow(() -> new ObjectNotFoundError("Greenhouse not found with id: "+id));
        if(!greenhouseList.contains(givenGreenhouse)){
            throw new BadRequestError("You don't have permission to delete this greenhouse");
        }
        greenhouseRepository.deleteById(id);
    }

    public List<GreenhouseResponseDto> getMyGreenhouses(String bearerToken, Boolean value) {
        User user = authServiceFeign.getCurrentUser(bearerToken);
        List<Greenhouse> greenhouseList = new ArrayList<>();
        List<GreenhouseResponseDto> response = new ArrayList<>();
        if(value!=null){
            greenhouseList = greenhouseRepository.findByOwner_IdOrderByNameAsc(user.getId());
        }else{
            greenhouseList = greenhouseRepository.findAllByOwnerId(user.getId());
        }
        greenhouseList.forEach(greenhouse -> {
            Arduino arduino = greenhouse.getArduino();
            Plant plant = arduino.getPlant();
            boolean optimalTemperature = isWithinRange(arduino.getTemperature(), plant.getMinimumTemperature(), plant.getMaximumTemperature());
            boolean optimalHumidity = isWithinRange(arduino.getHumidity(), plant.getMinimumHumidity(), plant.getMaximumHumidity());
            boolean optimalLight = isWithinRange(arduino.getLight(), plant.getMinimumLight(), plant.getMaximumLight());
            boolean optimalCarbonDioxide = isWithinRange(arduino.getCarbonDioxide(), plant.getMinimumCarbonDioxide(), plant.getMaximumCarbonDioxide());
            WarningDto warningDto = new WarningDto(optimalTemperature, optimalHumidity, optimalLight, optimalCarbonDioxide);
            GreenhouseResponseDto greenhouseResponseDto = new GreenhouseResponseDto(mapper.toDto(greenhouse),warningDto);
            response.add(greenhouseResponseDto);
        });
//        List<Greenhouse> greenhouseList = greenhouseRepository.findAllByOwnerId(user.getId());
//        return greenhouseList.stream().map(mapper::toDto).collect(Collectors.toList());
        return response;

    }

    private boolean isWithinRange(Float value, Float min, Float max) {
        return value != null && value >= min && value <= max;
    }



}
