package io.github.matheushenriquereiter.project.controller;

import io.github.matheushenriquereiter.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class UserWebController {
    private final UserService userService;

    public UserWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userList")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "userList";
    }
}
