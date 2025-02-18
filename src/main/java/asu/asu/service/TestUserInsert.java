package asu.asu.service;

import asu.asu.models.User;
import asu.asu.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class TestUserInsert implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    public TestUserInsert(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create a user
        User testUser = new User("ivan", "password123", "iran@email.com", "Test User", "test-picture-url");

        // Save the user
        userService.registerUser(testUser);

        // Print confirmation
        System.out.println("User inserted successfully: " + testUser);

        // Test Login with valid credentials
        Optional<User> loggedInUser = userService.loginUser("irannn@email.com", "password123");
        if (loggedInUser.isPresent()) {
            System.out.println("Login successful for: " + loggedInUser.get().getEmail());
        } else {
            System.out.println("Login failed for the email: test@email.com");
        }

        // Test Login with invalid credentials (wrong password)
        Optional<User> invalidLogin = userService.loginUser("tessssnst@email.com", "wrongPassword");
        if (invalidLogin.isPresent()) {
            System.out.println("Login successful for: " + invalidLogin.get().getEmail());
        } else {
            System.out.println("Login failed for the email: tesssst@email.com with wrong password");
        }

        // Test Login with non-existent user
        Optional<User> nonExistentLogin = userService.loginUser("nonexnstent@email.com", "anyPassword");
        if (nonExistentLogin.isPresent()) {
            System.out.println("Login successful for: " + nonExistentLogin.get().getEmail());
        } else {
            System.out.println("Login failed for the email: nonexistent@email.com");
        }
    }
}
