package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.feign.AuthServiceFeign;
import kz.iitu.smartgreenhouse.mapper.ArduinoMapper;
import kz.iitu.smartgreenhouse.mapper.GreenhouseMapper;
import kz.iitu.smartgreenhouse.model.Greenhouse;
import kz.iitu.smartgreenhouse.model.User;
import kz.iitu.smartgreenhouse.model.dto.GreenhouseDto;
import kz.iitu.smartgreenhouse.model.criteria.GreenhouseCriteria;
import kz.iitu.smartgreenhouse.model.dto.PageResponse;
import kz.iitu.smartgreenhouse.repository.GreenhouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GreenhouseService {
    private final GreenhouseRepository greenhouseRepository;
    private final AuthServiceFeign authServiceFeign;

    private final ArduinoMapper arduinoMapper;


    private final GreenhouseMapper mapper;

    public GreenhouseService(GreenhouseRepository greenhouseRepository, AuthServiceFeign authServiceFeign, ArduinoMapper arduinoMapper, GreenhouseMapper mapper) {
        this.greenhouseRepository = greenhouseRepository;
        this.authServiceFeign = authServiceFeign;
        this.arduinoMapper = arduinoMapper;
        this.mapper = mapper;
    }

    public Greenhouse save(GreenhouseDto greenhouseDto,String token) {
        Greenhouse greenhouse = new Greenhouse();
        greenhouse.setName(greenhouseDto.getName());
        User currenUser = authServiceFeign.getCurrentUser(token);
        greenhouse.setOwner(currenUser);
        greenhouse.setArduino(arduinoMapper.toEntity(greenhouseDto.getArduino()));
        return greenhouseRepository.save(greenhouse);
    }

    public Optional<Greenhouse> partialUpdate(GreenhouseDto greenhouseDto) {
        return greenhouseRepository
                .findById(greenhouseDto.getId())
                .map(existingGreenhouse -> {
                    existingGreenhouse.setName(greenhouseDto.getName());
                    existingGreenhouse.setOwner(greenhouseDto.getOwner());
                    existingGreenhouse.setArduino(arduinoMapper.toEntity(greenhouseDto.getArduino()));
                    return existingGreenhouse;
                })
                .map(greenhouseRepository::save);
    }

    public PageResponse<Greenhouse> findAllPageable(GreenhouseCriteria greenhouseCriteria) {
        PageResponse<Greenhouse> response = new PageResponse<>();
        Page<Greenhouse> greenhousePage = greenhouseRepository.findAll(
                PageRequest.of(
                        greenhouseCriteria.getPage() > 0 ? greenhouseCriteria.getPage() - 1 : 0,
                        greenhouseCriteria.getSize()
                )
        );
        response.setTotalCount(greenhousePage.getTotalElements());
        response.setTotalPages(greenhousePage.getTotalPages());
        response.setItems(greenhousePage.getContent());
        return response;
    }

    public Optional<GreenhouseDto> findById(Long id) {
        return greenhouseRepository.findById(id).map(mapper::toDto);
    }
}
