package io.github.matheushenriquereiter.project.model;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Type;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String email;
}
