package com.stankin.lab6;

import com.stankin.lab6.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final UserRepository userRepository;

    MainController (
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("userName", userRepository.getCurrentUser().getUserName());

        return "index";
    }
}
