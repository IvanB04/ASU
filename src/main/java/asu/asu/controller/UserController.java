package asu.asu.controller;

import asu.asu.models.User;
import asu.asu.repository.UserRepository;
import asu.asu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller

public class UserController {

    @Autowired
    private UserService userService;
    private UserRepository userRepository;
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model) {
        Optional<User> loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (loggedInUser.isPresent()) {
            model.addAttribute("user", loggedInUser);
            return "home";  // After login, show home page
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";  // Return to login page if credentials are incorrect
    }
}
