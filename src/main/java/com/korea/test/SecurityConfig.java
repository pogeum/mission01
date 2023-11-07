package com.korea.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //스프링의 환경설정 파일임을 의미하는 애너테이션
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
//  @EnableWebSecurity 애너테이션을 사용하면 내부적으로 SpringSecurityFilterChain이 동작하여 URL 필터가 적용된다.
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))

        ;
        return http.build();
    }
    //추가한 .formLogin 메서드는 스프링 시큐리티의 로그인 설정을 담당하는 부분으로
    // 로그인 페이지의 URL은 /user/login이고 로그인 성공시에 이동하는
    // 디폴트 페이지는 루트 URL(/)임을 의미한다.

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
//        AuthenticationManager 빈을 생성했다. AuthenticationManager는 스프링 시큐리티의 인증을 담당한다.
//        AuthenticationManager는 사용자 인증시 앞에서 작성한 UserSecurityService와 PasswordEncoder를 사용한다.
    }

}
