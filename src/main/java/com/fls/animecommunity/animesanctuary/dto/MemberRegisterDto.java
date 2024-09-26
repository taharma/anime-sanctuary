package com.fls.animecommunity.animesanctuary.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterDto {
	
	@NotEmpty(message = "아이디는 필수 입력값입니다")
	@Pattern(regexp = "^[a-z0-9]{5,20}$",message = "아이디는 영어 소문자와 숫자만 사용하여 5~20자리여야 합니다.")
    private String username;
	
	@NotEmpty(message = "비밀번호는 필수 입력값입니다")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    
	@NotEmpty(message = "이름은 필수 입력값입니다")
	private String name;
	
	@NotEmpty(message = "이메일은 필수 입력값입니다")
    @Email(message = "유효한 이메일 주소여야 합니다")
    private String email;
	
	@NotEmpty(message = "성별은 필수 입력값입니다")
    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "성별은 MALE, FEMALE, OTHER 중 하나여야 합니다.")
    private String gender;
	
	@Past(message = "생년월일은 과거의 날짜여야 합니다.")
    private LocalDate birth;
}
