package com.fls.animecommunity.animesanctuary.dto;

import com.fls.animecommunity.animesanctuary.model.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequestDto {

    private Member member;

    @NotBlank
    private String content;
}
