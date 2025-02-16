package asu.asu.controller;

import asu.asu.models.User;
import asu.asu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        // Log the incoming request parameters to see if they are being received correctly
        System.out.println("Received registration data:");
        System.out.println("Email: " + email);
        System.out.println("Username: " + username);
        System.out.println("Name: " + name);
        System.out.println("Picture URL: " + picture);
        System.out.println("Password: " + password); // Be careful with logging passwords in production

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
        User user = userService.loginUser(email, password);
        if (user != null) {
            return "redirect:/home";  // Redirect to home page on successful login
        }
        return "redirect:/login?error=true";  // Redirect back to login on failure
    }
}
