package com.stankin.lab6;

import com.stankin.lab6.models.User;
import com.stankin.lab6.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserRepository userRepository;

    UserController (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getCurrentUser")
    public User onUserRequested () {
        return this.userRepository.getCurrentUser();
    }
}
