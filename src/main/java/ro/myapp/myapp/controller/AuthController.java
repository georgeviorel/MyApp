package ro.myapp.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ro.myapp.myapp.dto.RegisterRequest;
import ro.myapp.myapp.entity.User;
import ro.myapp.myapp.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ro.myapp.myapp.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email deja existent";
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username deja existent";
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // momentan fara criptare
        // user.setPassword(request.getPassword());

        // cu criptare
        user.setPassword(
                encoder.encode(request.getPassword())
        );

        userRepository.save(user);

        return "Utilizator creat";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return "Email sau parola invalida";
        }

        if (!encoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return "Email sau parola invalida";
        }

        return "Login reusit";
    }
}

