package choi.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor // Argument가 없는 기본 생성자를 만든다.
@AllArgsConstructor // 모든 멤버 변수에 대한 Argument가 있는 생성자를 만든다.
@Getter // 멤버 변수들에 대한 Getter 함수들을 만든다.
public class UpdateArticleRequest {
    private String title;
    private String content;
}
