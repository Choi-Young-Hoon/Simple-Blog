package choi.project.controller;

import choi.project.domain.Article;
import choi.project.dto.AddArticleRequest;
import choi.project.dto.UpdateArticleRequest;
import choi.project.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper; // Json으로 직렬화, 역직렬화를 위한 클래스
    @Autowired
    private WebApplicationContext context;
    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());

        List<Article> articles = this.blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다")
    @Test
    public void findAllArticles() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        this.blogRepository.save(
                Article.builder()
                .title(title)
                .content(content)
                .build()
        );

        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("findArticle: 블로그 글 조회에 성공한다")
    @Test
    public void findArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = this.blogRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        final ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders.get(url, savedArticle.getId())
        );

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다")
    @Test
    public void deleteArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = this.blogRepository.save(
            Article.builder()
                    .title(title)
                    .content(content)
                    .build()
        );

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(url, savedArticle.getId())
        ).andExpect(MockMvcResultMatchers.status().isOk());

        List<Article> articles = this.blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "TEST_TITLE";
        final String content = "TEST_CONTENT";

        Article savedArticle = this.blogRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        final String newTitle = "NEW_TEST_TITLE";
        final String newContent = "NEW_TEST_CONTENT";
        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders.put(url, savedArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        Article article = this.blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }

}