package choi.project.examplethymeleaf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;
import java.util.List;

@Controller // 함수에서 뷰의 이름을 반환하는것을 알림 return "test" 일경우 test.html을 의미함
public class ExampleThymeleafContoller {
    @Getter // 멤버 변수들에 대한 Getter 함수 만듬.
    @Setter // 멤버 변수들에 대한 Setter 함수 만듬.
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }

    @GetMapping("/thymeleaf/example")
    // class Model
    // - 모델 객체는 뷰, 즉 HTML 쪽으로 값을 넘겨주는 객체다
    public String thymeleafExample(Model model) {
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서", "123"));

        // addAttribute() 메서드로 모델에 값을 저장한다.
        model.addAttribute("person", examplePerson);
        model.addAttribute("today", LocalTime.now());
        
        return "examplethymeleaf/example"; // example.html라는 뷰 조회
    }
}
