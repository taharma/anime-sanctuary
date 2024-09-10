package com.fls.animecommunity.animesanctuary.model.member;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(length = 100, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderType gender;

    @Column(length = 255)
    private String profileImage;

    @Column(length = 20)
    private String provider;  // ex: "google"
    
    @Column(length = 100)
    private String providerId;  // 소셜 로그인 시 제공자에서 받은 고유 ID
    
    @Enumerated(EnumType.STRING)
    private Role role;  // USER, ADMIN 등

    // 기본 생성자
    public Member() {}

    // 모든 필드를 포함한 생성자
    public Member(String username, String password, String name, LocalDate birth, String email, GenderType gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.gender = gender;
    }
    
    // 소셜 로그인 전용 생성자
    public Member(String name, String email, String provider, String providerId, Role role) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.username = email;
        this.birth = LocalDate.of(1900, 1, 1);  // 기본 생년월일 설정
        this.gender = GenderType.OTHER;  // 기본 성별 설정
    }

    // Getters and Setters 생략 (Lombok 사용으로 이미 포함)
}
