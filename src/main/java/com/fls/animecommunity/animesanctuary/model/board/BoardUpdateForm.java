package com.fls.animecommunity.animesanctuary.model.board;

import java.time.LocalDateTime;
import com.fls.animecommunity.animesanctuary.model.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BoardUpdateForm {
	
	private Long board_id;	
	@NotBlank
	private String title;
	@NotBlank
	private String contents;
	
	private Member member;
	private Long hit;
	private LocalDateTime created_time;
	
	
}
