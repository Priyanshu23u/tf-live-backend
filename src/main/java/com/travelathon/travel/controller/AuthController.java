package com.travelathon.travel.controller;

import com.travelathon.travel.dto.*;
import com.travelathon.travel.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest req) {

        service.signup(req.name, req.email, req.password);
        return "Signup successful";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {

        String token = service.login(req.email, req.password);
        return new AuthResponse(token);
    }
}
