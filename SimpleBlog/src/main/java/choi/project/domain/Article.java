package choi.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // JPA 에서 관리되는 객체로 DB table과 매핑됨
@Getter // 멤버 변수들에 대한 Getter 함수들 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected No argument 기본 생성자를 만듬.
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;
    
    @Builder // 빌더 패턴으로 객체 생성 매개 변수에 대한 접근 가능한 빌더를 만들어주는듯 하다 
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
