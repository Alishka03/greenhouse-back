package kz.iitu.auth.controller;

import kz.iitu.auth.dto.AuthRequest;
import kz.iitu.auth.dto.AuthResponse;
import kz.iitu.auth.dto.ProfileInfoDto;
import kz.iitu.auth.entity.UserCredential;
import kz.iitu.auth.service.AuthService;
import kz.iitu.auth.service.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController extends BaseController{

    private final AuthService service;
    private final AuthenticationManager authenticationManager;
    private final UserUtils userUtils;


    @PostMapping("/sign-up")
    public ResponseEntity<?> addNewUser(@RequestBody UserCredential user) throws Exception {
        AuthResponse auth = service.saveUser(user);
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/sign-in")
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

    @GetMapping("/get-current-user")
    public UserCredential getCurrentUser(@RequestHeader("Authorization") String bearerToken){
        String tokenWithoutPrefix = bearerToken.replace("Bearer ", "");
        return service.getUserFromToken(tokenWithoutPrefix);
    }

    @PutMapping("/update-profile-image")
    public UserCredential updateImage(@RequestBody String imageUrl,@RequestHeader("Authorization") String bearerToken){
        String tokenWithoutPrefix = bearerToken.replace("Bearer ", "");
        return service.updateProfileImage(imageUrl,tokenWithoutPrefix);
    }

    @PutMapping("/update-profile-info")
    public UserCredential updateProfileInfo(@RequestBody ProfileInfoDto infoDto, @RequestHeader("Authorization") String bearerToken){
        String tokenWithoutPrefix = bearerToken.replace("Bearer ", "");
        return service.updateProfileInfo(infoDto,tokenWithoutPrefix);
    }

}