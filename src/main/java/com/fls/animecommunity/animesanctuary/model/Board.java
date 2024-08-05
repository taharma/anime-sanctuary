package com.fls.animecommunity.animesanctuary.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@Entity
@Getter @Setter @ToString
//@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;					//board id
    private String title;				//board 제목
    private String contents;			//board 내용
    private Long hit;					//board 조회수
    private LocalDateTime createdTime;	//작성시간
}
