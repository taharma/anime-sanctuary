package com.fls.animecommunity.animesanctuary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fls.animecommunity.animesanctuary.model.noteLike.NoteLike;

public interface NoteLikeRepository extends JpaRepository<NoteLike, Long>{
	
	//noteId memberId 로 이미 좋아요를 눌렀는지 확인하는 쿼리메서드
	boolean existsByNoteIdAndMemberId(Long noteId, Long memberId);

	Long countByNoteId(Long noteId);

}
