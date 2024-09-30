package com.fls.animecommunity.animesanctuary.model.note.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/*
 * Note의 Requests 의 DataTransferObject
 */

@Getter
@Setter
public class NoteRequestsDto {
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(min = 1, max = 100, message = "제목은 1자 100자 이내로 작성해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9ぁ-んァ-ン一-龥々ー\\s]*$", message = "タイトルには、英数字、ひらがな、カタカナ、漢字、空白のみ使用できます。")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    @Size(min = 1, max = 10000, message = "内容は1文字以上10000文字以内で入力してください。")
    @Pattern(regexp = "^[a-zA-Z0-9ぁ-んァ-ン一-龥々ー\\s]*$", message = "内容には、英数字、ひらがな、カタカナ、漢字、空白のみ使用できます。")
    private String contents;

    private Long categoryId;

    private Long memberId;
}
