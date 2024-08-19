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
	
	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private GenderType gender;
	
	private LocalDate birth;
	
	@Column(length = 100)
	private String email;
}
