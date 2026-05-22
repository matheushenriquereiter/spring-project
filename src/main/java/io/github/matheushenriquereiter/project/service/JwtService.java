package io.github.matheushenriquereiter.project.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    // IMPORTANTE: Em produção, essa chave deve vir de variáveis de ambiente!
    // A chave precisa ter pelo menos 256 bits (32 caracteres) para o algoritmo HS256.
    private final String SECRET_STRING = "MinhaChaveSuperSecretaParaOProjetoJava2026!";

    // Gerando a chave secreta baseada na string acima
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    // Tempo de expiração do token (Ex: 1 hora)
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    /**
     * Gera o token JWT para um usuário específico.
     */
    public String gerarToken(String username) {
        return Jwts.builder()
                .subject(username) // Quem é o dono do token
                .issuedAt(new Date()) // Data de criação
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Data de expiração
                .signWith(key) // Assina o token com nossa chave secreta
                .compact(); // Constrói a string final do JWT
    }

    /**
     * Extrai o nome de usuário (subject) de um token existente.
     * Se o token for inválido, alterado ou estiver expirado, isso lançará uma exceção.
     */
    public String extrairUsername(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(key) // Configura a chave para validar a assinatura
                .build()
                .parseSignedClaims(token) // Faz o parse do token
                .getPayload(); // Pega o corpo do token (Claims)

        return payload.getSubject();
    }
}