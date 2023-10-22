package choi.project.service;

import choi.project.dto.AddArticleRequest;
import choi.project.domain.Article;
import choi.project.dto.UpdateArticleRequest;
import choi.project.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // 초기화 되지 않은 final 필드나 @NonNull이 붙은 필드에 대해 생성자를 생성해준다.
@Service // 빈으로 등록
public class BlogService {
    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return this.blogRepository.save(request.toEntity());
    }

    // 저장된 모든글 조회
    public List<Article> findAll() {
        return this.blogRepository.findAll();
    }

    public Article findById(long id) {
        // IllegalArgumentException - 메서드가 허용되지 않거나 부적절한 Argument에 대해 사용되는 예외
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        this.blogRepository.deleteById(id);
    }

    @Transactional // 트랜잭션 메서드
    public Article update(long id, UpdateArticleRequest request) {
        // IllegalArgumentException - 메서드가 허용되지 않거나 부적절한 Argument에 대해 사용되는 예외
        Article article = this.blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        article.update(request.getTitle(), request.getContent());
        return article;
    }

}
