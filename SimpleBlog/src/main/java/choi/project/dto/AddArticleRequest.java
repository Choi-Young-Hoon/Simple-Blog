package choi.project.dto;

import choi.project.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // No argument 기본 생성자를 만듬.
@AllArgsConstructor // 모든 멤버 변수를 인자로 받는 생성자를 만들어줌.
@Getter // 멤버 변수들에 대한 Getter 함수를 만들어줌
public class AddArticleRequest {
    private String title;
    private String content;
    private String author;

    public Article toEntity(String author) {
        return Article.builder()
                .title(this.title)
                .content(this.content)
                .author(author)
                .build();
    }
}
