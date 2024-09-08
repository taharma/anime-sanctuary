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
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;
import com.fls.animecommunity.animesanctuary.service.impl.MemberService;


@Configuration
public class SecurityConfig {

    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository; // 추가된 부분

    // 생성자를 통해 MemberRepository와 NoteRepository를 주입받음
    public SecurityConfig(MemberRepository memberRepository, NoteRepository noteRepository) {
        this.memberRepository = memberRepository;
        this.noteRepository = noteRepository; // 추가된 부분
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/members/**").permitAll()
                .requestMatchers("/api/notes/**").permitAll()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(this.oauth2UserService())
                )
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure")
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

            String provider = userRequest.getClientRegistration().getRegistrationId();
            String providerId = oAuth2User.getAttribute("sub");
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            MemberService memberService = new MemberService(memberRepository, passwordEncoder(), noteRepository); // 변경된 부분
            Member member = memberService.registerOrLoginWithSocial(name, email, provider, providerId);

            return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
                oAuth2User.getAttributes(),
                "email"
            );
        };
    }
}
