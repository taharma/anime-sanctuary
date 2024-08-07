package com.fls.animecommunity.animesanctuary.model.board;

import com.fls.animecommunity.animesanctuary.model.post.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/*
 * Board 게시판 자체를 나타내는 class
 * 따라서 id , title 2개의 필드를 가진다.
 * 뭔가 내용을 담을 게 필요한가?? 아닌가?
 */
@Entity
@Data
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
    private String title;
	
}
