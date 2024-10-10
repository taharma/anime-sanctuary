package com.fls.animecommunity.animesanctuary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
        .csrf(csrf -> csrf.disable()) // CSRF 비활성화
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/", "/login**", "/error**").permitAll() // 공용 경로 허용
                .requestMatchers("/api/members/register").permitAll() // 회원가입 경로도 허용
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // OPTIONS 메소드 허용
                .anyRequest().authenticated() // 나머지는 인증 필요
        );
    return http.build();
}


    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://127.0.0.1:5501"); // 프론트엔드 주소 허용
        configuration.addAllowedMethod("*"); // 모든 메소드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 인증 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 인코더 설정
    }
}
