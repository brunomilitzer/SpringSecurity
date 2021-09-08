package com.brunomilitzer.springsecurity.controller;

import com.brunomilitzer.springsecurity.model.User;
import com.brunomilitzer.springsecurity.repositories.UserRepository;
import com.brunomilitzer.springsecurity.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/showReg")
    public String showRegistrationPage() {

        return "registerUser";
    }

    @PostMapping("/registerUser")
    public String register(User user) {

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        return "login";
    }

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String email, String password) {

        if (securityService.login(email, password)) {
            return "index";
        }

        return "login";
    }
}
