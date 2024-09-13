package com.fls.animecommunity.animesanctuary.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fls.animecommunity.animesanctuary.dto.NoteLikeDto.NoteLikeRequestDto;
import com.fls.animecommunity.animesanctuary.dto.NoteLikeDto.NoteLikeResponseDto;
import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.noteLike.NoteLike;
import com.fls.animecommunity.animesanctuary.repository.MemberRepository;
import com.fls.animecommunity.animesanctuary.repository.NoteLikeRepository;
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;
import com.fls.animecommunity.animesanctuary.service.interfaces.NoteLikeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * 데이터베이스 저장소(예: JPA Repository)와의 상호작용을 구현
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteLikeServiceImpl implements NoteLikeService {

	private final NoteLikeRepository noteLikeRepository;
	private final NoteRepository noteRepository;
	private final MemberRepository memberRepository;

	@Override
	public NoteLikeResponseDto addLike(@Valid NoteLikeRequestDto noteLikeRequestDto) {
		// 호출확인
		log.info("call NoteLikeService addLike()");

		// 1. noteLikeRequestDto 안의 noteId , memberId 가져오기
		Long noteId = noteLikeRequestDto.getNoteId();
		Long memberId = noteLikeRequestDto.getMemberId();

		// 2. Id console
		log.info("noteId : {}", noteId);
		log.info("memberId : {}", memberId);

		// 3.객체가져오기
		Optional<Note> noteOptional = noteRepository.findById(noteId);
		Optional<Member> memberOptional = memberRepository.findById(memberId);

		// 4.null 검사 : 값이 존재하는지 확인하고, 없으면 예외 처리
		Note note = noteOptional.orElseThrow(() -> new RuntimeException("Note not found with id: " + noteId));
		Member member = memberOptional.orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

		// 데이터베이스에서 이미 좋아요를 눌렀는지 확인 (중복 방지)
		boolean alreadyLiked = noteLikeRepository.existsByNoteIdAndMemberId(noteId, memberId);

		// alreadyLiked가 False 면 조건문을 실행하도록
		if (!alreadyLiked) {
			// 좋아요 수를 +1 : NoteLike 생성 근대 객체를 직접 전달해야함 id가아니라
			noteLikeRepository.save(new NoteLike(note, member));
			log.info("좋아요 추가: noteId = {}, memberId = {}", noteId, memberId);
		} else {
			log.info("이미 좋아요를 누른 상태입니다: noteId = {}, memberId = {}", noteId, memberId);
		}
		
		// 현재 좋아요 수 계산 : 쿼리로 치면 [select count(*) FROM note_like] 객체의 수를 세는것  
	    Long likeCount = noteLikeRepository.countByNoteId(noteId);
	    
	 // 응답 생성
	    return new NoteLikeResponseDto(noteId, true, likeCount, "좋아요가 추가되었습니다.");
	}

	@Override
	public NoteLikeResponseDto removeLike(@Valid NoteLikeRequestDto noteLikeRequestDto) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public NoteLikeResponseDto getLikeStatus(Long noteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteLikeResponseDto getLikeCount(Long noteId) {
		// TODO Auto-generated method stub
		return null;
	}

}
