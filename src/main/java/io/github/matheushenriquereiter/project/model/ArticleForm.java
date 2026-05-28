package io.github.matheushenriquereiter.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleForm {
    private String title;
    private String resume;
}
