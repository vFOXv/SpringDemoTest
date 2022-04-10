package com.example.SpringDemo.controller;

import com.example.SpringDemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {
    private final UserService userService;

    public StartController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showAllUser(Model model) {
        model.addAttribute("myAllUsers", userService.getAllUsers());
        return "Start/start";
    }
}
