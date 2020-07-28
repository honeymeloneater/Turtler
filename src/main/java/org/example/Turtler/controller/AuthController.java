package org.example.Turtler.controller;

import lombok.AllArgsConstructor;
import org.example.Turtler.dto.RegisterRequest;
import org.example.Turtler.dto.AuthenticationResponse;
import org.example.Turtler.dto.LoginRequest;
import org.example.Turtler.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity(OK);
    }
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successully", OK);
    }
    @PostMapping("/login")
    public AuthenticationResponse login (@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
