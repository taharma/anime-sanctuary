package com.fls.animecommunity.animesanctuary.dto;

import com.fls.animecommunity.animesanctuary.model.Member;

public class MemberResponseDTO {
    private Long id;
    private String username;
    private String email;

    public MemberResponseDTO() {}

    public MemberResponseDTO(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
    }

    // Getters and Setters
}
