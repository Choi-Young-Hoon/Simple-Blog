package choi.project.config;

import choi.project.config.oauth.OAuth2UserCustomService;
import choi.project.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final UserDetailService userService;
    private final OAuth2UserCustomService oAuth2UserCustomService;


    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring() // 정적 리소스(이미지, HTML) 에만 설정하도록 다른 리소스들에 ignoring() 적용
                .requestMatchers(toH2Console()) // h2의 데이터를 확인하는데 사용하는 h2-console 하위 url 대상으로 ignoring() 메서드 사용
                .requestMatchers("/static/**");
               // .requestMatchers("/static/**"); // static 하위 경로에 있는 리소스 데이터를 사용하는데는 ignoring() 메서드 사용
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth  //////////////////////////////////// 폼 기반 로그인 설정 인증, 인가 설정
                        .requestMatchers("/login", "/signup", "/user").permitAll() // 해당 Url에 대해 누구나 접근 가능하게 한다.
                        .anyRequest().authenticated())// 모든 요청에 대해 인증
                .formLogin(form -> form //////////////////////////////////// 폼 기반 로그인 설정
                        .loginPage("/login") // 로그인 페이지 지정
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/articles")) // 로그인 성공시 이동할 URL 지정
                        //.permitAll()) // 누구나 접근 가능하게 한다. 해도되고 안해도됨 requestMatchers에 지정해둠.
                .logout(logout -> logout //////////////////////////////////// 로그아웃 설정
                        .logoutSuccessUrl("/login") // 로그아웃 성공시 /login으로 이동
                        .invalidateHttpSession(true)) // 로그아웃 후 세션을 전체 삭제할지 여부 설정
                .csrf(csrf -> csrf
                        .disable()) // CSRF 공격을 방지위해서는 활성화 해야하지만 실습을 편하게 하기위해 비활성화
                .oauth2Login(oauth2Configure -> oauth2Configure
                        .loginPage("/login")
                        .successHandler(successHandler())
                        .defaultSuccessUrl("/articles")
                        .userInfoEndpoint(info -> info
                                .userService(this.oAuth2UserCustomService)))
                .build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailService userDetailService) throws Exception {
        http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(this.userService) // 사용자 정보 서비스 설정
                .passwordEncoder(bCryptPasswordEncoder);
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String id = defaultOAuth2User.getAttribute("id").toString();
            String body = """
                    {"id":"%s"}
                    """.formatted(id);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            PrintWriter writer = response.getWriter();
            writer.println(body);
            writer.flush();
        });
    }
}
