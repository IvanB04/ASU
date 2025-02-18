package asu.asu.controller;

import asu.asu.models.User;
import asu.asu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/Auth")
public class AuthController {

    @Autowired
    private UserService userService;  // Autowire UserService

    // Show registration page
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";  // Render the register.html page
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String picture,
                           @RequestParam String username) {


        // Using the constructor to create a new User object
        User user = new User(username, password, email, name, picture);

        // Save the user using the service (which will hash the password)
        userService.registerUser(user);

        // Log after saving the user
        System.out.println("User object created and saved!");

        return "redirect:/login";  // After successful registration, redirect to login page
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return "redirect:/login?error=emptyFields";  // Error for empty inputs
        }
        System.out.println("EMAIL: " + email);  // Check the email value being passed
        Optional<User> user = userService.loginUser(email, password);
        if (user.isPresent()) {
            return "redirect:/home";  // Successfully logged in
        } else {
            return "redirect:/login?error=invalidCredentials";  // Invalid credentials
        }
    }


}

