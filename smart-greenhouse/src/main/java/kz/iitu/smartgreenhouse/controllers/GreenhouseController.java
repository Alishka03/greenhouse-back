package kz.iitu.smartgreenhouse.controllers;

import kz.iitu.smartgreenhouse.feign.AuthServiceFeign;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/greenhouse")
public class GreenhouseController {

    private final AuthServiceFeign authServiceFeign;

    public GreenhouseController(AuthServiceFeign authServiceFeign) {
        this.authServiceFeign = authServiceFeign;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGreenhouse(@RequestHeader("Authorization") String bearerToken){
        return ResponseEntity.ok(authServiceFeign.getCurrentUser(bearerToken));
    }
}
