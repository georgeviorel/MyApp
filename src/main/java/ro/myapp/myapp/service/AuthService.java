package ro.myapp.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.myapp.myapp.dto.LoginRequest;
import ro.myapp.myapp.dto.LoginResponse;
import ro.myapp.myapp.dto.RegisterRequest;
import ro.myapp.myapp.dto.UserProfileResponse;
import ro.myapp.myapp.entity.User;
import ro.myapp.myapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email deja existent";
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username deja existent";
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(
                encoder.encode(request.getPassword())
        );

        userRepository.save(user);

        return "Utilizator creat";
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(
                request.getEmail()
        ).orElse(null);

        if (user == null) {
            return "Email sau parola invalida";
        }

        if (!encoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return "Email sau parola invalida";
        }
        String token = jwtService.generateToken(
                user.getEmail()
        );

        //return token;
        return jwtService.generateToken(
                user.getEmail()
        );

    }

    public UserProfileResponse getProfile(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow();

//        System.out.println("EMAIL DIN TOKEN: " + email);
//        System.out.println("USER GASIT: " + user.getEmail());
        return new UserProfileResponse(
                user.getUsername(),
                user.getEmail()
        );
    }

}