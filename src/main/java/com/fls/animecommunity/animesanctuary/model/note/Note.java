package com.fls.animecommunity.animesanctuary.model.note;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

import com.fls.animecommunity.animesanctuary.model.category.Category;
import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

/*
 * 
 */
@Entity
@Data
@NoArgsConstructor
public class Note extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //제목
    @Column(nullable = false)
    private String title;
    
    //내용
    @Column(nullable = false )
    private String contents;
    
//    //회원 사용자
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
    
    //조회수
    private Long hit;	
    
    //카테고리
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    // 노트와 연관된 태그들
    @ElementCollection
    private List<String> tags;
    
    public Note(NoteRequestsDto requestsDto) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
    }

	

	public void update(NoteRequestsDto requestsDto) {
		setContents(requestsDto.getContents());
		setTitle(requestsDto.getTitle());
		
	}
    
    }
    
    
    
    

