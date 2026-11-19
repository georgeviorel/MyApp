package ro.myapp.myapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/api/profile")
    public String profile() {
        return "Acces permis la endpoint protejat!";
    }
}