package com.fls.animecommunity.animesanctuary.model.note.dto;

import com.fls.animecommunity.animesanctuary.model.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoteRequestsDto {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    private Long categoryId;

    private Member member;  // 작성자 정보 추가
}
