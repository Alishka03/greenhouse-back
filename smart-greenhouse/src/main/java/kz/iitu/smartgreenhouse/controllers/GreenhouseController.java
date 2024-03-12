package kz.iitu.smartgreenhouse.controllers;

import kz.iitu.smartgreenhouse.feign.AuthServiceFeign;
import kz.iitu.smartgreenhouse.model.dto.GreenhouseDto;
import kz.iitu.smartgreenhouse.service.GreenhouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/greenhouse")
public class GreenhouseController {

    private final AuthServiceFeign authServiceFeign;

    private final GreenhouseService greenhouseService;

    public GreenhouseController(AuthServiceFeign authServiceFeign, GreenhouseService greenhouseService) {
        this.authServiceFeign = authServiceFeign;
        this.greenhouseService = greenhouseService;
    }

    @PostMapping()
    public ResponseEntity<?> createGreenhouse(@RequestHeader("Authorization") String bearerToken, @RequestBody GreenhouseDto dto){
        return ResponseEntity.ok(greenhouseService.save(dto,bearerToken));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(greenhouseService.findById(id));
    }
}
