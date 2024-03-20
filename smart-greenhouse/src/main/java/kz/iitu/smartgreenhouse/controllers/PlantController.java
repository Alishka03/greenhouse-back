package kz.iitu.smartgreenhouse.controllers;

import kz.iitu.smartgreenhouse.model.Plant;
import kz.iitu.smartgreenhouse.model.criteria.PlantCriteria;
import kz.iitu.smartgreenhouse.model.dto.PageResponse;
import kz.iitu.smartgreenhouse.model.dto.PlantDto;
import kz.iitu.smartgreenhouse.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/plants")
public class PlantController extends BaseController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping()
    public ResponseEntity<?> createPlant(@RequestBody PlantDto plantDto){
        return ResponseEntity.ok(plantService.save(plantDto));
    }

    @PutMapping()
    public ResponseEntity<?> updatePlant(@RequestBody PlantDto plantDto){
        Optional<Plant> result = plantService.partialUpdate(plantDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public ResponseEntity<PageResponse<Plant>> findAllPageable(PlantCriteria plantCriteria) {
        return new ResponseEntity<>(plantService.findAllPageable(plantCriteria), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(plantService.findById(id),HttpStatus.OK);
    }
}
