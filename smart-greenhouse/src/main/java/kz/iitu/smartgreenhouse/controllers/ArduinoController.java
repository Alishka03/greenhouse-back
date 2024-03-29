package kz.iitu.smartgreenhouse.controllers;

import kz.iitu.smartgreenhouse.model.Arduino;
import kz.iitu.smartgreenhouse.model.criteria.ArduinoCriteria;
import kz.iitu.smartgreenhouse.model.criteria.ArduinoData;
import kz.iitu.smartgreenhouse.model.dto.ArduinoDto;
import kz.iitu.smartgreenhouse.model.dto.PageResponse;
import kz.iitu.smartgreenhouse.service.ArduinoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/arduino")
public class ArduinoController extends BaseController {
    private final ArduinoService arduinoService;

    public ArduinoController(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    @PostMapping
    public ResponseEntity<Arduino> createArduino(@RequestBody ArduinoDto arduinoDto) {
        Arduino createdArduino = arduinoService.save(arduinoDto);
        return new ResponseEntity<>(createdArduino, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Arduino> partialUpdateArduino(@RequestBody ArduinoDto arduinoDto) {
        Optional<Arduino> updatedArduino = arduinoService.partialUpdate(arduinoDto);
        return updatedArduino.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PageResponse<Arduino>> getAllArduinos(ArduinoCriteria arduinoCriteria
    ) {
        PageResponse<Arduino> arduinoPage = arduinoService.findAllPageable(arduinoCriteria);
        return ResponseEntity.ok(arduinoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arduino> getArduinoById(@PathVariable Long id) {
        Optional<Arduino> arduino = arduinoService.findById(id);
        return arduino.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insert-data")
    public ResponseEntity<Arduino> insertData(ArduinoData data){
        return new ResponseEntity<>(arduinoService.insertData(data),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArduino(@PathVariable Long id){
        arduinoService.deleteArduino(id);
        return ResponseEntity.ok().build();
    }

}
