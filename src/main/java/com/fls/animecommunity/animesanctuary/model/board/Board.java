package com.fls.animecommunity.animesanctuary.model.board;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import com.fls.animecommunity.animesanctuary.model.board.dto.BoardRequestsDto;
import com.fls.animecommunity.animesanctuary.model.board.dto.BoardResponseDto;
import com.fls.animecommunity.animesanctuary.model.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String contents;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    private String password;
    
    private Long hit;	
    
    public Board(BoardRequestsDto requestsDto) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.author = requestsDto.getAuthor();
        this.password = requestsDto.getPassword();
    }

	public Board(BoardResponseDto requestsDto) {
		// TODO Auto-generated constructor stub
	}
    
    }
    
    
    
    

