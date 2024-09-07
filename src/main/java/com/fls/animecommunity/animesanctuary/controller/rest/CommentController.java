package com.fls.animecommunity.animesanctuary.controller.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.dto.CommentRequestDto;
import com.fls.animecommunity.animesanctuary.dto.CommentResponseDto;
import com.fls.animecommunity.animesanctuary.service.interfaces.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notes/{noteId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long noteId,
                                                         @RequestBody CommentRequestDto commentrequestDto) {
        CommentResponseDto comment = commentService.addComment(noteId, commentrequestDto);
        return ResponseEntity.ok(comment);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long noteId) {
        List<CommentResponseDto> comments = commentService.getCommentsByNoteId(noteId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId,
                                              @RequestBody CommentRequestDto requestDto) {
        commentService.updateComment(commentId, requestDto.getMemberId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @RequestParam Long memberId) {
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.ok().build();
    }
}
