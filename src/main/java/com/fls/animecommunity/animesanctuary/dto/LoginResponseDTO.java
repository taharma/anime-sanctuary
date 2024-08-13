package com.fls.animecommunity.animesanctuary.dto;

public class LoginResponseDTO {
    private String message;
    private MemberResponseDTO member;

    public LoginResponseDTO(String message, MemberResponseDTO member) {
        this.message = message;
        this.member = member;
    }

    // Getters and Setters
}
