package kz.iitu.auth.controller;

import kz.iitu.auth.dto.AuthRequest;
import kz.iitu.auth.dto.AuthResponse;
import kz.iitu.auth.dto.RequestResponseDto;
import kz.iitu.auth.entity.UserCredential;
import kz.iitu.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody UserCredential user) throws Exception {
        AuthResponse auth = service.saveUser(user);
        if(auth==null){
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST).
                    body(new RequestResponseDto("User exists by email:" + user.getEmail(), LocalDateTime.now()));
        }
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return ResponseEntity.ok(service.generateToken(authRequest.getEmail()));
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }

    @GetMapping("/reset-password/{email}")
    public ResponseEntity<?> getCodeViaEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getCodeViaEmail(email));
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<?> changePassword(@PathVariable String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("code") Long code){
        return ResponseEntity.ok(service.changePassword(email, password, code));
    }
}
