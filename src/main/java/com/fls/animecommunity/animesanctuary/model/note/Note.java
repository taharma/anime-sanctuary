package com.fls.animecommunity.animesanctuary.model.note;

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
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/*
 * 게시글(Post)을 나타내는 class
 * 필드 : id title contents author password hit(조회수)
 * 
 */
@Entity
@Data
@NoArgsConstructor
public class Note extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String contents;
    
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
    @Column(nullable = false)
    private String password;
    
    private Long hit;	
    
    public Note(NoteRequestsDto requestsDto) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.password = requestsDto.getPassword();
    }

	

	public void update(NoteRequestsDto requestsDto) {
		setContents(requestsDto.getContents());
		setTitle(requestsDto.getTitle());
		
	}
    
    }
    
    
    
    

