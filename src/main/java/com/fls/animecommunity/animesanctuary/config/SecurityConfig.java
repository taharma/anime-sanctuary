package com.fls.animecommunity.animesanctuary.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.repository.MemberRepository;
import com.fls.animecommunity.animesanctuary.service.impl.MemberService;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final MemberRepository memberRepository;

    public SecurityConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // CSRF 비활성화
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정 추가
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // 모든 요청을 허용
            )
            .oauth2Login().disable();  // OAuth2 로그인 임시 비활성화

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*"));  // 모든 출처 허용
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowCredentials(true);  // 필요 시 true 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // OAuth2UserService는 나중에 다시 활성화할 수 있도록 주석 처리하거나 비활성화
//    @Bean
//    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
//        return userRequest -> {
//            OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
//
//            String provider = userRequest.getClientRegistration().getRegistrationId();  // "google"
//            String providerId = oAuth2User.getAttribute("sub");
//            String email = oAuth2User.getAttribute("email");
//            String name = oAuth2User.getAttribute("name");
//
//            MemberService memberService = new MemberService(memberRepository, passwordEncoder());
//            Member member = memberService.registerOrLoginWithSocial(name, email, provider, providerId);
//
//            return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
//                oAuth2User.getAttributes(),
//                "email"
//            );
//        };
//    }
}

}
