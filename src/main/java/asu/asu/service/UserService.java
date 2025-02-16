package asu.asu.service;

import asu.asu.models.User;
import asu.asu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service  // No need for @Component since @Service already includes it
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(User user) {
        // Log the user object before saving
        System.out.println("Attempting to save user: " + user.getUsername());

        try {
            // If you're using password encoding, log the hashed password (but don't log the original password)
            System.out.println("Hashed password: " + passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);  // Save the user to the database
            System.out.println("User saved successfully!");
        } catch (Exception e) {
            // Log any errors that occur during saving
            System.err.println("Error saving user: " + e.getMessage());
        }
    }


    public User loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        // **Check if user exists and if the password matches**
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        }

        return null;
    }
}
