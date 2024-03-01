package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.feign.AuthServiceFeign;
import kz.iitu.smartgreenhouse.model.Greenhouse;
import kz.iitu.smartgreenhouse.repository.GreenhouseRepository;
import org.springframework.stereotype.Service;

@Service
public class GreenhouseService {
    private final GreenhouseRepository greenhouseRepository;
    private final AuthServiceFeign authServiceFeign;
    public GreenhouseService(GreenhouseRepository greenhouseRepository, AuthServiceFeign authServiceFeign) {
        this.greenhouseRepository = greenhouseRepository;
        this.authServiceFeign = authServiceFeign;
    }

    private Greenhouse createGreenHouse(){
        //TODO
        return null;
    }
}
