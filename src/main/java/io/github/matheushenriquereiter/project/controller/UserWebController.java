package io.github.matheushenriquereiter.project.controller;

import io.github.matheushenriquereiter.project.dto.UserDTO;
import io.github.matheushenriquereiter.project.model.User;
import io.github.matheushenriquereiter.project.model.UserForm;
import io.github.matheushenriquereiter.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

        return "user-list";
    }

    @GetMapping("/showUser")
    public String showUser(Model model) {
        model.addAttribute("user", userService.getFirstUser());

        return "show-user";
    }

    @GetMapping("/register")
    public String Register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "register";
    }

    @PostMapping("/register/save")
    public String createUser(Model model, @ModelAttribute() UserForm userForm) {
        model.addAttribute("userForm", userForm);

        UserDTO userDTO = new UserDTO(userForm.getName(), userForm.getEmail());
        userService.saveUser(userDTO);

        return "register-success";
    }
}
