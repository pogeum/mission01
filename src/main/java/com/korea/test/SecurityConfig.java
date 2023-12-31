package com.korea.test;

import com.korea.test.user.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //스프링의 환경설정 파일임을 의미하는 애너테이션
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
//  @EnableWebSecurity 애너테이션을 사용하면 내부적으로 SpringSecurityFilterChain이 동작하여 URL 필터가 적용된다.
@EnableMethodSecurity(prePostEnabled = true) //for 로그인필요한 메서드들의 애너테이션 'PreAuthenticated' . 자동으로로그인화면으로 보내줌?
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login") //스프링시큐리티 기본제공 로그인페이지말고 내가만든 로그인페이지로 이동시키기
                        .defaultSuccessUrl("/"))
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .loginPage("/member/login")
//                        .userInfoEndpoint(
//                                userInfoEndpoint -> userInfoEndpoint
//                                        .userService(oAuth2UserService)) //로그인성공후 화면

                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/"))
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                );

//                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))

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
    //비번 암호화하는 ㅋㅗ드.

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
//        AuthenticationManager 빈을 생성했다. AuthenticationManager는 스프링 시큐리티의 인증을 담당한다.
//        AuthenticationManager는 사용자 인증시 앞에서 작성한 UserSecurityService와 PasswordEncoder를 사용한다.
    }

}
