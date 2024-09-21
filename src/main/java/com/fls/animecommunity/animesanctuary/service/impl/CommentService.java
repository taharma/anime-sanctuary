package com.fls.animecommunity.animesanctuary.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fls.animecommunity.animesanctuary.model.comment.Comment;
import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.repository.CommentRepository;
import com.fls.animecommunity.animesanctuary.repository.MemberRepository;
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, NoteRepository noteRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.noteRepository = noteRepository;
    }

    @Transactional
    public Comment addComment(String username, Long noteId, String content, Long parentCommentId) {
        Member member = memberRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new IllegalArgumentException("Note not found"));

        Comment comment = new Comment();
        comment.setMember(member);
        comment.setNote(note);
        comment.setContent(content);

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByNoteId(Long noteId) {
        return commentRepository.findByNoteId(noteId);
    }

    public List<Comment> getCommentsByMemberId(Long memberId) {
        return commentRepository.findByMemberId(memberId);
    }

    public List<Comment> getReplies(Long parentCommentId) {
        return commentRepository.findByParentCommentId(parentCommentId);
    }
}
