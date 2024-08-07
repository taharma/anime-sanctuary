package com.fls.animecommunity.animesanctuary.model.post;

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

import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.model.post.dto.BoardRequestsDto;
import com.fls.animecommunity.animesanctuary.model.post.dto.BoardResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/*
 * 게시글(Post)을 나타내는 class
 * 필드 : id title contents author password hit(조회수)
 * author는 추후 Member 객체를 직접 가지고 있게끔 바꿔야함
 */
@Entity
@Data
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    public Post(BoardRequestsDto requestsDto) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.author = requestsDto.getAuthor();
        this.password = requestsDto.getPassword();
    }

	

	public void update(BoardRequestsDto requestsDto) {
		setAuthor(requestsDto.getAuthor());
		setContents(requestsDto.getContents());
		setTitle(requestsDto.getTitle());
		
	}
    
    }
    
    
    
    

