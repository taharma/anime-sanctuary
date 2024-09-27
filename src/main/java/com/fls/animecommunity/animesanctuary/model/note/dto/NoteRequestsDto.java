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
    @NotBlank(message = "タイトルは必須です。")
    @Size(min = 5, max = 100, message = "タイトルは5文字以上100文字以内で入力してください。")
    @Pattern(regexp = "^[a-zA-Z0-9ぁ-んァ-ン一-龥々ー\\s]*$", message = "タイトルには、英数字、ひらがな、カタカナ、漢字、空白のみ使用できます。")
    private String title;

    @NotBlank(message = "内容は必須です。")
    @Size(min = 10, max = 1000, message = "内容は10文字以上1000文字以内で入力してください。")
    @Pattern(regexp = "^[a-zA-Z0-9ぁ-んァ-ン一-龥々ー\\s]*$", message = "内容には、英数字、ひらがな、カタカナ、漢字、空白のみ使用できます。")
    private String contents;

    // カテゴリーID (注意: カテゴリー id = 1 anime など)
    private Long categoryId;

    // 作成者ID
    private Long memberId;
}
