package kz.iitu.smartgreenhouse.controllers;

import kz.iitu.smartgreenhouse.aspect.annotations.LoggingAspect;
import kz.iitu.smartgreenhouse.model.Arduino;
import kz.iitu.smartgreenhouse.model.criteria.ArduinoCriteria;
import kz.iitu.smartgreenhouse.model.criteria.ArduinoData;
import kz.iitu.smartgreenhouse.model.dto.ArduinoDto;
import kz.iitu.smartgreenhouse.model.dto.PageResponse;
import kz.iitu.smartgreenhouse.model.dto.WarningDto;
import kz.iitu.smartgreenhouse.model.wrapper.CustomResponse;
import kz.iitu.smartgreenhouse.service.ArduinoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/arduino")
@Slf4j
public class ArduinoController extends BaseController {
    private final ArduinoService arduinoService;

    public ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    @PostMapping
    public ResponseEntity<ArduinoDto> createArduino(@RequestBody ArduinoDto arduinoDto) {
        ArduinoDto createdArduino = arduinoService.save(arduinoDto);
        return new ResponseEntity<>(createdArduino, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ArduinoDto> partialUpdateArduino(@RequestBody ArduinoDto arduinoDto) {
        Optional<ArduinoDto> updatedArduino = arduinoService.partialUpdate(arduinoDto);
        return updatedArduino.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PageResponse<ArduinoDto>> getAllArduinos(ArduinoCriteria arduinoCriteria
    ) {
        PageResponse<ArduinoDto> arduinoPage = arduinoService.findAllPageable(arduinoCriteria);
        return ResponseEntity.ok(arduinoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArduinoDto> getArduinoById(@PathVariable Long id) {
        Optional<ArduinoDto> arduino = arduinoService.findById(id);
        return arduino.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @LoggingAspect
    @PostMapping("/insert-data/test")
    public CustomResponse<?> insertData(ArduinoData data){
        return new CustomResponse<>(true,arduinoService.insertingData(data),null);
    }

    @LoggingAspect
    @PostMapping("/insert-data")
    public WarningDto insertDataTest(ArduinoData data){
        return arduinoService.insertDataTest(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArduino(@PathVariable Long id){
        arduinoService.deleteArduino(id);
        return ResponseEntity.ok().build();
    }

}
