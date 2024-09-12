package com.fls.animecommunity.animesanctuary.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.dto.NoteLikeDto.NoteLikeRequestDto;
import com.fls.animecommunity.animesanctuary.dto.NoteLikeDto.NoteLikeResponseDto;
import com.fls.animecommunity.animesanctuary.service.interfaces.NoteLikeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/*
좋아요 기능 api endpoint :

좋아요 추가: 
POST api/notes/{noteId}/noteLike
좋아요 취소: 
DELETE api/notes/{noteId}/noteLike
좋아요 상태 조회: 
GET api/notes/{noteId}/noteLike
좋아요 수 조회: 
GET api/notes/{noteId}/noteLike/count
 * 
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/notes/{noteId}/noteLike")
public class NoteLikeController {
	
	private final NoteLikeService noteLikeService;
	
	@PostMapping					//Body에 좋아요에 대한 정보를 담아 요청을 보낸다.
    public ResponseEntity<?> insert(@RequestBody @Valid NoteLikeRequestDto noteLikeRequestDto) {
        
		log.info("Success insert()");
		//그다음 noteLikeService의 insert호출 하여 noteLikeResponseDto를 생성
		NoteLikeResponseDto noteLikeResponseDto = noteLikeService.insert(noteLikeRequestDto);
		
		log.info("noteLikeResponseDto : {} ", noteLikeResponseDto);
		//그후 client에게 noteLikeResponseDto를 리턴
        return ResponseEntity.ok(noteLikeRequestDto);
    }

    @DeleteMapping					//Body에 좋아요에 대한 정보를 담아 요청을 보낸다.
    public ResponseEntity<?> delete(@RequestBody @Valid NoteLikeRequestDto noteLikeRequestDto) {
    	
    	log.info("Success delete()");
    	//그다음 noteLikeService의 insert호출 하여 noteLikeResponseDto를 생성
    	NoteLikeResponseDto noteLikeResponseDto = noteLikeService.insert(noteLikeRequestDto);
    	
    	log.info("noteLikeResponseDto : {} ", noteLikeResponseDto);
    	//그후 client에게 noteLikeResponseDto를 리턴
    	return ResponseEntity.ok(noteLikeRequestDto);
    }
	
	
	
}
