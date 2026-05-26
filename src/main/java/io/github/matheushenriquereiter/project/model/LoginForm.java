package io.github.matheushenriquereiter.project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginForm {
    @NotBlank(message = "The email must not be empty")
    @Email(message = "The email must be valid.")
    private String email;

    @NotBlank(message = "The password must not be empty")
    private String password;

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }
}
