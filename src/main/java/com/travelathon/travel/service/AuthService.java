package com.travelathon.travel.service;

import com.travelathon.travel.entity.User;
import com.travelathon.travel.repository.UserRepository;
import com.travelathon.travel.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository repo,
                       PasswordEncoder encoder,
                       JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public void signup(String name, String email, String password) {

        if (repo.findByEmail(email).isPresent())
            throw new RuntimeException("Email exists");

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(encoder.encode(password));

        repo.save(u);
    }

    public String login(String email, String password) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword()))
            throw new RuntimeException("Wrong password");

        return jwtUtil.generateToken(email);
    }
}
