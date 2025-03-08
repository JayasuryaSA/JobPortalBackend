package com.example.springRest.controller;

import com.example.springRest.model.User;
import com.example.springRest.repo.UserRepo;
import com.example.springRest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class UserController {


    @Autowired
    private UserService service;

    @Autowired
    private UserRepo repo;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return Map.of("error", "Username is required");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return Map.of("error", "Password is required");
        }

        if (service.isUserExists(user.getUsername())) {
            return Map.of("error", "User already exists");
        }

        user.setRole("ROLE_USER");
        service.saveUser(user);
        return Map.of("message", "User registered successfully");
    }




    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        if (service.authenticateUser(user.getUsername(), user.getPassword())) {
            User loggedInUser = service.getUserByUsername(user.getUsername());
            return Map.of(
                    "message", "Login successful!",
                    "username", loggedInUser.getUsername(),
                    "role", loggedInUser.getRole()
            );
        }
        return Map.of("error", "Invalid credentials!");
    }


    @PostMapping("/createAdmin")
    public String createAdmin(@RequestBody User user) {
        return service.createAdmin(user);

    }

}





