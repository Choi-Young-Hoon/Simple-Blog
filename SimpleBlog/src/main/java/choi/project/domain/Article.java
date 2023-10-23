package choi.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity // JPA 에서 관리되는 객체로 DB table과 매핑됨
@EntityListeners(AuditingEntityListener.class) // @CreadtedDate @LastModifiedDate 사용하기 위해 추가
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

    @CreatedDate // 엔티티가 생성될때 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

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
