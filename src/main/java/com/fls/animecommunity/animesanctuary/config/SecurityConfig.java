package com.fls.animecommunity.animesanctuary.config;

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

import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.repository.MemberRepository;
import com.fls.animecommunity.animesanctuary.service.impl.MemberService;

@Configuration
public class SecurityConfig {

    private final MemberRepository memberRepository;

    // 생성자를 통해 MemberRepository를 주입받음
    public SecurityConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // /api/members/register, /api/members/login은 비인증 사용자도 접근 가능
                .requestMatchers("/api/members/register", "/api/members/login").permitAll()
                // 그 외의 /api/members/** 경로는 인증된 사용자만 접근 가능
                .requestMatchers("/api/members/**").authenticated()
                
                // /api/notes에서 조회(GET), 검색(GET) 요청은 비인증 사용자도 가능
                .requestMatchers("/api/notes", "/api/notes/search").permitAll()  // Note 조회 및 검색은 비인증 사용자도 가능
                .requestMatchers("/api/notes/**").authenticated()  // 노트 생성, 수정, 삭제 등은 인증된 사용자만 접근 가능

                // Swagger 관련 URL은 비인증 사용자도 접근 가능
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**").permitAll()

                // 나머지 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(this.oauth2UserService())
                )
                .defaultSuccessUrl("/loginSuccess")  // 성공 후 리다이렉트할 URL
                .failureUrl("/loginFailure")  // 로그인 실패 시 리다이렉트할 URL
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

            String provider = userRequest.getClientRegistration().getRegistrationId();  // "google"
            String providerId = oAuth2User.getAttribute("sub");  // 구글의 사용자 ID
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            // Gender와 Birth 기본값을 설정한 생성자를 사용
            MemberService memberService = new MemberService(memberRepository, passwordEncoder());
            Member member = memberService.registerOrLoginWithSocial(name, email, provider, providerId);

            return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
                oAuth2User.getAttributes(),
                "email"  // 기본 키
            );
        };
    }
}
