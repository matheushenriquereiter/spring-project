package io.github.matheushenriquereiter.project.repository;

import io.github.matheushenriquereiter.project.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
