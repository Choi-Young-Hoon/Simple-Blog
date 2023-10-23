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
import org.springframework.web.bind.annotation.RequestParam;

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
        return "blogview/articleList"; // templates/blogview/articleList.html 뷰 조회
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id,  Model model) {
        Article article = this.blogService.findById(id);

        model.addAttribute("article", new ArticleViewResponse(article));
        return "blogview/article"; // templates/blogview/article.html 뷰 조회
    }

    @GetMapping("/new-article")
    // id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑(id는 없을 수도 있음)
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) { // id 가 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = this.blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "blogview/newArticle"; // templates/blogview/newArticle.html 뷰 조회
    }
}
