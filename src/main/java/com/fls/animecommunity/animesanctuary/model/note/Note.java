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

import com.fls.animecommunity.animesanctuary.dto.noteDto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.dto.noteDto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.category.Category;
import com.fls.animecommunity.animesanctuary.model.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

/*
 * 
 */
@Entity
@Data
@NoArgsConstructor
public class Note extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = false)
    private String contents;

    // 조회수
    private Long hit;

    // 카테고리
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = true)
    private Category category;

    // 노트와 연관된 태그들
    @ElementCollection
    private List<String> tags;

    public Note(NoteRequestsDto requestsDto) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        if (requestsDto.getCategoryId() != null) {
            // Category를 가져와서 설정하는 로직 추가 (예: CategoryService를 사용해 가져오기)
            // this.category = categoryService.getCategoryById(requestsDto.getCategoryId());
        }
    }

    public void update(NoteRequestsDto requestsDto) {
        setContents(requestsDto.getContents());
        setTitle(requestsDto.getTitle());
        if (requestsDto.getCategoryId() != null) {
            // Category를 가져와서 설정하는 로직 추가 (예: CategoryService를 사용해 가져오기)
            // this.category = categoryService.getCategoryById(requestsDto.getCategoryId());
        } else {
            this.category = null;
        }
    }
}

    
    
    
    

