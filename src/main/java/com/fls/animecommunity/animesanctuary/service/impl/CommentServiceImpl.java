package com.fls.animecommunity.animesanctuary.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fls.animecommunity.animesanctuary.dto.CommentRequestDto;
import com.fls.animecommunity.animesanctuary.dto.CommentResponseDto;
import com.fls.animecommunity.animesanctuary.exception.ResourceNotFoundException;
import com.fls.animecommunity.animesanctuary.model.comment.Comment;
import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.repository.CommentRepository;
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;
import com.fls.animecommunity.animesanctuary.service.interfaces.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NoteRepository noteRepository;

    @Override
    public CommentResponseDto addComment(Long noteId, CommentRequestDto requestDto) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found"));

        Comment comment = new Comment(note, requestDto.getMember(), requestDto.getContent());
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Override
    public List<CommentResponseDto> getCommentsByNoteId(Long noteId) {
        return commentRepository.findByNoteId(noteId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateComment(Long commentId, Long memberId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("You are not the author of this comment.");
        }

        comment.updateContent(requestDto.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long memberId) {
        commentRepository.deleteByIdAndMemberId(commentId, memberId);
    }
}
