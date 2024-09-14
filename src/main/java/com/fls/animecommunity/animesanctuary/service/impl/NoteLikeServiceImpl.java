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
 * 
 * addLike : memberId가 null일 때의 처리 필요 (비로그인용 사용자)
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteLikeServiceImpl implements NoteLikeService {

	private final NoteLikeRepository noteLikeRepository;
	private final NoteRepository noteRepository;
	private final MemberRepository memberRepository;
	
	//addLike
	@Override
	public NoteLikeResponseDto addLike(@Valid NoteLikeRequestDto noteLikeRequestDto) {
		// 호출확인
		log.info("call NoteLikeService addLike()");

		// 1. noteLikeRequestDto 안의 noteId , memberId 가져오기
		Long noteId = noteLikeRequestDto.getNoteId();
		Long memberId = noteLikeRequestDto.getMemberId();

		// 2. Id console 출력
		log.info("noteId : {}", noteId);
		log.info("memberId : {}", memberId);

		// 3. Note 객체 가져오기
		Optional<Note> noteOptional = noteRepository.findById(noteId);
		Note note = noteOptional.orElseThrow(() -> new RuntimeException("Note not found with id: " + noteId));

		// 4. memberId가 null인 경우 : 익명 사용자 처리 (분기점)
		if (memberId == null) {
			// memberId가 null이면 익명 사용자로 처리
			// 익명 사용자의 경우 중복 체크는 noteId로만 확인
			//해당 noteId, MemberIdIsNull인 NoteLike가 있는지 없는지 확인
			boolean isLikedByAnonymous = noteLikeRepository.existsByNoteIdAndMemberIdIsNull(noteId);

			if (!isLikedByAnonymous) {
				// 익명 사용자는 Member 없이 좋아요를 추가
				noteLikeRepository.save(new NoteLike(note, null));
				log.info("익명 사용자가 좋아요를 추가했습니다: noteId = {}", noteId);
			} else {
				log.info("익명 사용자가 이미 좋아요를 눌렀습니다: noteId = {}", noteId);
			}
		} else {
			// 5. memberId가 있을 경우 처리
			Optional<Member> memberOptional = memberRepository.findById(memberId);
			Member member = memberOptional.orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

			// 이미 좋아요를 눌렀는지 확인 (중복 방지)
			boolean isLikedByMember = noteLikeRepository.existsByNoteIdAndMemberId(noteId, memberId);

			if (!isLikedByMember) {
				// 좋아요 추가
				noteLikeRepository.save(new NoteLike(note, member));
				log.info("좋아요 추가: noteId = {}, memberId = {}", noteId, memberId);
			} else {
				log.info("이미 좋아요를 누른 상태입니다: noteId = {}, memberId = {}", noteId, memberId);
			}
		}
		
		// 현재 좋아요 수 계산
	    Long likeCount = noteLikeRepository.countByNoteId(noteId);

		// 응답 생성
	    return new NoteLikeResponseDto(noteId, true, likeCount, "좋아요가 추가되었습니다.");
	}
	
	//removeLike
	@Override
	public NoteLikeResponseDto removeLike(@Valid NoteLikeRequestDto noteLikeRequestDto) {
	    // 호출확인
	    log.info("call NoteLikeService removeLike()");
	    
	    // 1. noteLikeRequestDto 안의 noteId , memberId 가져오기
	    Long noteId = noteLikeRequestDto.getNoteId();
	    Long memberId = noteLikeRequestDto.getMemberId();
	    
	    // 2. Id console 출력
	    log.info("noteId : {}", noteId);
	    log.info("memberId : {}", memberId);

	    // 3. Note 객체 가져오기
	    Optional<Note> noteOptional = noteRepository.findById(noteId);
	    Note note = noteOptional.orElseThrow(() -> new RuntimeException("Note not found with id: " + noteId));

	    if (memberId == null) {
	        // 4. memberId가 null인 경우 익명 사용자 처리
	        boolean isLikedByAnonymous = noteLikeRepository.existsByNoteIdAndMemberIdIsNull(noteId);

	        if (isLikedByAnonymous) {
	            // 익명 사용자의 좋아요 삭제
	            NoteLike noteLike = noteLikeRepository.findByNoteIdAndMemberIdIsNull(noteId)
	                    .orElseThrow(() -> new RuntimeException("Like not found for noteId: " + noteId));
	            noteLikeRepository.delete(noteLike);
	            log.info("익명 사용자의 좋아요가 삭제되었습니다: noteId = {}", noteId);
	        } else {
	            log.info("익명 사용자가 좋아요를 누르지 않았습니다: noteId = {}", noteId);
	            throw new RuntimeException("Like not found for anonymous user on noteId: " + noteId);
	        }
	    } else {
	        // 5. memberId가 있는 경우 처리
	        Optional<Member> memberOptional = memberRepository.findById(memberId);
	        Member member = memberOptional.orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

	        boolean isLikedByMember = noteLikeRepository.existsByNoteIdAndMemberId(noteId, memberId);

	        if (isLikedByMember) {
	            // 사용자의 좋아요 삭제
	            NoteLike noteLike = noteLikeRepository.findByNoteIdAndMemberId(noteId, memberId)
	                    .orElseThrow(() -> new RuntimeException("Like not found for noteId: " + noteId + " and memberId: " + memberId));
	            noteLikeRepository.delete(noteLike);
	            log.info("좋아요가 삭제되었습니다: noteId = {}, memberId = {}", noteId, memberId);
	        } else {
	            log.info("해당 사용자가 좋아요를 누르지 않았습니다: noteId = {}, memberId = {}", noteId, memberId);
	            throw new RuntimeException("Like not found for noteId: " + noteId + " and memberId: " + memberId);
	        }
	    }

	    // 현재 좋아요 수 계산
	    Long likeCount = noteLikeRepository.countByNoteId(noteId);
	    
	    // 응답 생성
	    return new NoteLikeResponseDto(noteId, false, likeCount, "좋아요가 삭제되었습니다.");
	}

	//getLikeStatus
	@Override
	public NoteLikeResponseDto getLikeStatus(Long noteId) {
		// 호출확인
	    log.info("call NoteLikeService getLikeStatus()");
	    
	    // 2. Id console 출력
	    log.info("noteId : {}", noteId);

	    // 3. Note 객체 가져오기
	    Optional<Note> noteOptional = noteRepository.findById(noteId);
	    Note note = noteOptional.orElseThrow(() -> new RuntimeException("Note not found with id: " + noteId));
		
	    
	    return null;
	}
	
	//getLikeCount
	@Override
	public NoteLikeResponseDto getLikeCount(Long noteId) {
		// TODO Auto-generated method stub
		return null;
	}

}

