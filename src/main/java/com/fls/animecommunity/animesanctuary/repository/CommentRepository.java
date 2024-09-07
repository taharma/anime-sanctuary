package com.fls.animecommunity.animesanctuary.repository;

import com.fls.animecommunity.animesanctuary.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByNoteId(Long noteId);

    void deleteByIdAndMemberId(Long id, Long memberId);

	boolean existsByIdAndMemberId(Long commentId, Long memberId);
}
