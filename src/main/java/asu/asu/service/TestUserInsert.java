package asu.asu.service;

import asu.asu.models.User;
import asu.asu.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        User testUser = new User("ivan", "password123", "test@email.com", "Test User", "test-picture-url");

        // Save the user
        userService.registerUser(testUser);

        // Print confirmation
        System.out.println("User inserted successfully: " + testUser);
    }
}
