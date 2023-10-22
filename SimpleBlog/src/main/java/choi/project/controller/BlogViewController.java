package choi.project.controller;

import choi.project.domain.Article;
import choi.project.dto.ArticleViewResponse;
import choi.project.service.BlogService;
import choi.project.dto.ArticleListViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller // 함수에서 뷰의 이름을 반환하는것을 알림 return "test" 일경우 test.html을 의미함
@RequiredArgsConstructor // 초기화 되지 않은 final 필드나 @NonNull이 붙은 필드에 대해 생성자를 생성해준다.
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = this.blogService.findAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();

        model.addAttribute("articles", articles);
        return "blogview/articleList"; // articleList.html 뷰 조회
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id,  Model model) {
        Article article = this.blogService.findById(id);

        model.addAttribute("article", new ArticleViewResponse(article));
        return "blogview/article";
    }
}
