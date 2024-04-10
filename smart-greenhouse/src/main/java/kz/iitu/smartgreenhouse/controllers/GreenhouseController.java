package kz.iitu.smartgreenhouse.controllers;

import kz.iitu.smartgreenhouse.model.dto.GreenhouseDto;
import kz.iitu.smartgreenhouse.service.GreenhouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/greenhouse")
public class GreenhouseController extends BaseController{


    private final GreenhouseService greenhouseService;

    public GreenhouseController(GreenhouseService greenhouseService) {
        this.greenhouseService = greenhouseService;
    }

    @PostMapping()
    public ResponseEntity<?> createGreenhouse(@RequestHeader("Authorization") String bearerToken, @RequestBody GreenhouseDto dto) {
        return ResponseEntity.ok(greenhouseService.save(dto, bearerToken));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(greenhouseService.findById(id));
    }

    @PutMapping
    public ResponseEntity<?> updateGreenhouse(@RequestBody GreenhouseDto dto,@RequestHeader("Authorization") String bearerToken){
        return ResponseEntity.ok(greenhouseService.updateGreenhouse(dto,bearerToken));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGreenhouse(@PathVariable Long id , @RequestHeader("Authorization") String bearerToken){
        greenhouseService.deleteGreenhouse(id,bearerToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyGreenhouses( @RequestHeader("Authorization") String bearerToken , @RequestParam(value = "sortByName",required = false) Boolean value){
        return ResponseEntity.ok(greenhouseService.getMyGreenhouses(bearerToken,value));
    }

    //TODO : 1)GREENHOUSE SORTING BY NAME ,TRY TO : PLANTS IN IT 2)ARDUINO SORTING BY NAME; 3)WARNING OF GREENHOUSE!!!
}
