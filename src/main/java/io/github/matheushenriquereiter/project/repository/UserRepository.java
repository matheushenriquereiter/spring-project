package io.github.matheushenriquereiter.project.repository;

import io.github.matheushenriquereiter.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User getFirstById(Long id);
}
