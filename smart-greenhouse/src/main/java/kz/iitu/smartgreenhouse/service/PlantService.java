package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.mapper.PlantMapper;
import kz.iitu.smartgreenhouse.model.Plant;
import kz.iitu.smartgreenhouse.model.criteria.PlantCriteria;
import kz.iitu.smartgreenhouse.model.dto.PageResponse;
import kz.iitu.smartgreenhouse.model.dto.PlantDto;
import kz.iitu.smartgreenhouse.repository.PlantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantService {
    private final PlantRepository plantRepository;

    private final PlantMapper plantMapper;

    public PlantService(PlantRepository plantRepository, PlantMapper plantMapper) {
        this.plantRepository = plantRepository;
        this.plantMapper = plantMapper;
    }

    public Plant save(PlantDto plantDto){
        //TODO
        Plant plant = plantMapper.toEntity(plantDto);
        plant = plantRepository.save(plant);
        return plant;
    }

    public Optional<Plant> partialUpdate(PlantDto plantDto){
        return plantRepository
                .findById(plantDto.getId())
                .map(existingPlant -> {
                    plantMapper.partialUpdate(existingPlant,plantDto);
                    return existingPlant;
                })
                .map(plantRepository::save);
    }


    public PageResponse<Plant> findAllPageable(PlantCriteria plantCriteria) {
        PageResponse<Plant> response = new PageResponse<>();
        Page<Plant> postPage = plantRepository.findAll(
                PageRequest.of(
                        plantCriteria.getPage() > 0 ? plantCriteria.getPage() - 1 : 0,
                        plantCriteria.getSize()
                )
        );
        response.setTotalCount(postPage.getTotalElements());
        response.setTotalPages(postPage.getTotalPages());
        response.setItems(postPage.getContent());
        return response;
    }
}
