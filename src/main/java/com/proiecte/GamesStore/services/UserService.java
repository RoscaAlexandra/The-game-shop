package com.proiecte.GamesStore.services;

import com.proiecte.GamesStore.domain.User;
import com.proiecte.GamesStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private UserRepository userRepository;

    private UserDetailServiceImp userDetailServiceImp;

    @Autowired
    UserService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    String a="1234";

    public void seedUsers() {
        seedUser("Szmetty", "1234", "Szmeteanca", "Eduard", "email", "ceva ", true, "ADMIN");
    }

    private void seedUser(String username, String password, String firstName, String lastName, String email, String address, boolean deactivated, String role) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAddress(address);
            user.setDeactivated(deactivated);
            user.setRole(role);
            userRepository.save(user);
        }
    }

    String pwd = "ADMIN";
    BCryptPasswordEncoder bc= new BCryptPasswordEncoder();
    String hashPwd= bc.encode(pwd);

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(u -> users.add(new User(u)));
        return users;
    }

    public User getCurrentUser(Principal principal) {
        return new User(userRepository.findByUsername(principal.getName()));
    }

    public User updateUser(Principal principal, Long id, User updatedUser) {
        Optional<User> requestedUser = userRepository.findById(id);
        User currentUser = userRepository.findByUsername(principal.getName());
        if (requestedUser != null && requestedUser.get().getId() == currentUser.getId()) {
            currentUser.setFirstName(updatedUser.getFirstName());
            currentUser.setLastName(updatedUser.getLastName());
            currentUser.setEmail(updatedUser.getEmail());
            currentUser.setAddress(updatedUser.getAddress());
            return new User(userRepository.save(currentUser));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
    }


}