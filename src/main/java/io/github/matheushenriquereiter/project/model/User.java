package io.github.matheushenriquereiter.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class User {
    @Id
    private int id;
    private String name;
    private String email;
}
