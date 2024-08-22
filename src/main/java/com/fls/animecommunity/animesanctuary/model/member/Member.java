package com.fls.animecommunity.animesanctuary.model.member;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * Member class : 회원 사용자 클래스
 * 기본적인 vaildation
 * id 의 GenerationType.IDENTITY
 */


@Entity
@Getter @Setter @ToString
public class Member {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 데이터베이스에서 자동으로 생성되는 ID
	private Long id;  
    
    @Column(length = 20, nullable = false, unique = true)
	// 사용자 이름
	private String username;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(length = 50, nullable = false)
	// 사용자 이름 (first name, last name 분리 가능)
	private String name; 
	
	@Column(nullable = false)
	private LocalDate birth;
	
	@Column(length = 100)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GenderType gender;
	
	@Column(length = 255)
    private String profileImage;
	
	// 기본 생성자
    public Member() {}

    // 모든 필드를 포함한 생성자
    public Member(String username, String password, String name, LocalDate birthdate, String email, GenderType gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.gender = gender;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }
    
    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
