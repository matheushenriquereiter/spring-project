package io.github.matheushenriquereiter.project.dto;

import io.github.matheushenriquereiter.project.model.Article;
import io.github.matheushenriquereiter.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleDTO {
    private Long id;
    private String title;
    private String resume;
    private Set<User> users = new HashSet<>();

    public Article toEntity() {
        return new Article(this.getId(), this.getTitle(), this.getResume(), this.getUsers());
    }
}
