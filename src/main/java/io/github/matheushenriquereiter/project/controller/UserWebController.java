package io.github.matheushenriquereiter.project.controller;

import io.github.matheushenriquereiter.project.dto.TokenDTO;
import io.github.matheushenriquereiter.project.dto.UserDTO;
import io.github.matheushenriquereiter.project.model.User;
import io.github.matheushenriquereiter.project.model.UserForm;
import io.github.matheushenriquereiter.project.model.UserLogin;
import io.github.matheushenriquereiter.project.service.JwtService;
import io.github.matheushenriquereiter.project.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web")
public class UserWebController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserWebController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
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
    public String showRegistrationForm(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        UserDTO userDTO = new UserDTO(userForm.getName(), userForm.getEmail(), userForm.getPassword());
        userService.saveUser(userDTO);

        return "redirect:register-success";
    }

    @GetMapping("/login")
    public String login(Model model) {
        UserLogin userLogin = new UserLogin();
        model.addAttribute("userLogin", userLogin);

        return "login-form";
    }

    @PostMapping("/login")
    public String processLogin(
            @Valid @ModelAttribute UserLogin userLogin,
            BindingResult result,
            HttpServletResponse response,
            Model model) {

        // 1. Verifica se houve erros de validação básicos do formulário (ex: campos vazios)
        if (result.hasErrors()) {
            return "login-form";
        }

        // 2. Valida as credenciais diretamente no banco (Substitui a chamada da API)
        boolean credenciaisValidas = userService.validarCredenciais(
                userLogin.getEmail(),
                userLogin.getPassword()
        );

        // 3. Se a senha estiver errada, devolve para o form de login com uma mensagem
        if (!credenciaisValidas) {
            model.addAttribute("erro", "E-mail ou senha incorretos.");
            return "login-form";
        }

        // 4. Credenciais corretas! Geramos o Token JWT
        String token = jwtService.gerarToken(userLogin.getEmail());

        // 5. Criamos o Cookie para salvar o token no navegador do usuário
        Cookie jwtCookie = new Cookie("jwt_token", token);
        jwtCookie.setHttpOnly(true);  // Segurança máxima: impede que hackers roubem o cookie via JavaScript
        jwtCookie.setPath("/");       // O token será válido em todas as páginas do site
        jwtCookie.setMaxAge(60 * 60);  // Tempo de vida do Cookie: 1 hora (em segundos)

        // 6. Anexamos o Cookie na resposta que vai voltar para o navegador
        response.addCookie(jwtCookie);

        // 7. Redireciona para a página interna protegida (Use sempre 'redirect:' após um POST de sucesso)
        return "redirect:/web/userList";
    }
}
