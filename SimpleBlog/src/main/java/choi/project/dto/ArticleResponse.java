package choi.project.dto;

import choi.project.domain.Article;
import lombok.Getter;

@Getter // 멤버 변수들에 대한 Getter 함수를 만들어줌
public class ArticleResponse {
    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
