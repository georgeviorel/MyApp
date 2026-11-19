package ro.myapp.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.myapp.myapp.dto.LoginRequest;
import ro.myapp.myapp.dto.LoginResponse;
import ro.myapp.myapp.dto.RegisterRequest;
import ro.myapp.myapp.dto.UserProfileResponse;
import ro.myapp.myapp.service.AuthService;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")public class AuthController {

   @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(
    @RequestBody RegisterRequest request) {

        return authService.register(request);
   }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @GetMapping("/me")
    public UserProfileResponse me(
            Authentication authentication
    ) {

        return authService.getProfile(
                authentication.getName()
        );
    }

//    @GetMapping("/secure")
//    public String secure(Authentication authentication) {
//
//        return "Salut " + authentication.getName();
//    }

}