package com.example.project.controller;

import com.example.project.domain.User;
import com.example.project.repository.UserRepository;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userSevice;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email,
                          User user, Map<String, Object> model) {
        if (userRepo.findByUsername(username) != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        if (username.equals("") || password.equals("") || email.equals("")){
            model.put("message", "Please fill all fields");
            return "registration";
        }

        else{
            userSevice.addUser(user);
            model.put("message", "Check you email. Visit link with activation code");
            model.put("error", false);
            return "login";
        }
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userSevice.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        model.addAttribute("error", false);
        return "login";
    }
}