package kz.iitu.smartgreenhouse.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class AuthController {
    @GetMapping
    public String test(){
        return "authenticated";
    }
}
