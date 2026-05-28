package io.github.matheushenriquereiter.project.dto;

import io.github.matheushenriquereiter.project.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "The name must not be empty")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters long")
    private String name;

    @NotBlank(message = "The email must not be empty")
    @Email(message = "The email must be valid.")
    private String email;

    @NotBlank(message = "The password must not be empty")
    private String password;

    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return new User(this.getId(), this.getName(), this.getEmail(), this.getPassword());
    }
}
