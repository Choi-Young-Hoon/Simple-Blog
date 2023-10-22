package choi.project.controller;

import choi.project.domain.Article;
import choi.project.dto.AddArticleRequest;
import choi.project.dto.ArticleResponse;
import choi.project.dto.UpdateArticleRequest;
import choi.project.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor // 초기화 되지 않은 final 필드나 @NonNull이 붙은 필드에 대해 생성자를 생성해준다.
@RestController // Rest API 서버 Controller를 위한 어노테이션
public class BlogApiController {
    private final BlogService blogService;

    // ResponseEntity 는 RequestEntity(HTTP Request)에 대한 응답데이터를 포함하는 클래스다
    // 따라서 HttpStatus, HttpHeaders, HttpBody 를 포함한다.
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = this.blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = this.blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articles);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = this.blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        this.blogService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updateArticle = this.blogService.update(id, request);

        return ResponseEntity.ok().body(updateArticle);
    }
}
