package choi.project.dto;

import choi.project.domain.Article;
import lombok.Getter;

@Getter // 멤버 변수들에 대한 Getter 함수를 만들어줌
public class ArticleListViewResponse {
    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
