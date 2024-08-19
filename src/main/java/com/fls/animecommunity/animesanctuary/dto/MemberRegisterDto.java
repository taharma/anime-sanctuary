package com.fls.animecommunity.animesanctuary.dto;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class MemberRegisterDto {
    private String username;
    private String password;
    private String name;
    private String email;
    private String gender;
    private LocalDate birth;
}
