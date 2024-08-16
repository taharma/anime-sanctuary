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
	@Column(length = 20)
	private String username;	//아이디 = 유저네임
	
	@Column(length = 20, nullable = false)
	private String password;	//패스워드
	
	@Column(length = 50, nullable = false)
	private String name;		//이름
	
	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private GenderType gender;		//성별
	
	private LocalDate birth;		//생년월일
	
	@Column(length = 100)
	private String email;		//이메일
}
