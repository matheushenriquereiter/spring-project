package io.github.matheushenriquereiter.project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationForm {
    @NotBlank(message = "The name must not be empty")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters long")
    private String name;

    @NotBlank(message = "The email must not be empty")
    @Email(message = "The email must be valid.")
    private String email;

    @NotBlank(message = "The password must not be empty")
    private String password;

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }
}
