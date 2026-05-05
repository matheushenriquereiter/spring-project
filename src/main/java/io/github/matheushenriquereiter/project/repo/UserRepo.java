package io.github.matheushenriquereiter.project.repo;

import io.github.matheushenriquereiter.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
