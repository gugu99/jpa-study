package com.gugu.study.comment.controller;

import com.gugu.study.comment.dto.CommentRequest;
import com.gugu.study.comment.dto.CommentResponse;
import com.gugu.study.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 특정 게시글 댓글 리스트 조회

    // 댓글 등록
    @PostMapping("/api/board/{id}/comment")
    public ResponseEntity<CommentResponse> add(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody CommentRequest commentRequest,
                                               @PathVariable(name = "id") Long boardId) {
        Long userId = Long.parseLong(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.add(commentRequest, userId, boardId));
    }

    // 댓글 수정

    // 댓글 삭제
}
