package com.proiecte.GamesStore.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable=false,unique=true)
    private String username;

    @Column(nullable = false)
    private String password;


    private String role;


    private String address;


    private String email;


    private String firstName;

    private String lastName;


    private Boolean deactivated = false;

    public User(User byUsername) {
    }

    public User(String username, String passwordHash, String role, String address) {
        super();
        this.username = username;
        this.password = passwordHash;
        this.role = role;
        this.address = address;
    }

    public User() {
    }
}
