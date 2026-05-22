package io.github.matheushenriquereiter.project.model;

public class LoginRequest {
    private String username;
    private String password;

    // Construtores, Getters e Setters
    public LoginRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}