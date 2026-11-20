package ro.myapp.myapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.myapp.myapp.dto.UserProfileResponse;
import ro.myapp.myapp.service.AuthService;

@RestController
public class ProfileController {

    private final AuthService authService;

    public ProfileController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/api/profile")
    public UserProfileResponse profile(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return authService.getProfile(email);
    }
}