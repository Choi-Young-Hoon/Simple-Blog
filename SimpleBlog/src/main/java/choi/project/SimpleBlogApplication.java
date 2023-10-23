package choi.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {SecurityFilterAutoConfiguration.class})
@EnableJpaAuditing // @CreatedDate, @LastModifiedDate 애너테이션을 활성화 하기위한 애너테이션
public class SimpleBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleBlogApplication.class, args);
    }
}