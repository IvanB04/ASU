package asu.asu.controller;

import asu.asu.models.User;
import asu.asu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    // Show registration page
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";  // Render the register.html page
    }
    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String pictureUrl,
                           @RequestParam String username) {
        // Using the constructor to create a new User object
        User user = new User(username, password, email, name, pictureUrl);

        // Save the user using the service
        userService.registerUser(user);

        return "redirect:/login";  // After successful registration, redirect to login page
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        User user = userService.loginUser(email, password);
        if (user != null) {
            return "redirect:/home";  // Redirect to home page on successful login
        }
        return "redirect:/login?error=true";  // Redirect back to login on failure
    }
}
