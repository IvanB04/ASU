package asu.asu.service;

import asu.asu.models.User;
import asu.asu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service  // No need for @Component since @Service already includes it
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password before saving
        userRepository.save(user);
    }



    public Optional<User> loginUser(String email, String password) {
        // Log email and password for debugging
        System.out.println("Attempting login for email: " + email);

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            // Check if password matches
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return user; // Authentication successful
            } else {
                System.out.println("Password mismatch for email: " + email);
            }
        } else {
            System.out.println("No user found with email: " + email);
        }

        return Optional.empty(); // Authentication failed
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
    }
}
