package asu.asu.models;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String name;
    private String picture;


    public User() {

    }

    public User(String username, String password, String email, String name, String picture) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.picture = picture;
    }


}
