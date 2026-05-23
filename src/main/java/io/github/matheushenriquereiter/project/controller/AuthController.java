package io.github.matheushenriquereiter.project.controller;

import io.github.matheushenriquereiter.project.dto.TokenDTO;
import io.github.matheushenriquereiter.project.dto.UserDTO;
import io.github.matheushenriquereiter.project.service.UserService;
import io.github.matheushenriquereiter.project.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    // Injetando os serviços pelo construtor
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        // 1. Validamos as credenciais no banco de dados (usando o BCrypt)
        boolean credenciaisValidas = userService.validarCredenciais(userDTO.getEmail(), userDTO.getPassword());

        // 2. Se a senha ou e-mail estiverem errados, barramos aqui
        if (!credenciaisValidas) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha incorretos.");
        }

        // 3. Se passou, o usuário é legítimo! Geramos o Token JWT usando o e-mail dele
        String token = jwtService.gerarToken(userDTO.getEmail());

        // 4. Devolvemos o token com status 200 OK
        return ResponseEntity.ok(new TokenDTO(token));
    }
}