package kz.iitu.smartgreenhouse.feign;

import kz.iitu.smartgreenhouse.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "http://localhost:9898") // Replace with the actual URL of your auth service
public interface AuthServiceFeign {

    @GetMapping("/auth/get-current-user")
    User getCurrentUser(@RequestHeader("Authorization") String bearerTokenString);

}
