package com.gugu.study.comment.controller;

import com.gugu.study.comment.dto.CommentRequest;
import com.gugu.study.comment.dto.CommentResponse;
import com.gugu.study.comment.service.CommentService;
import com.gugu.study.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 특정 게시글 댓글 리스트 조회
    @GetMapping("/api/board/{id}/comment")
    public ResponseEntity<List<CommentResponse>> getBoardComments(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                  @PathVariable(name = "id") Long boardId) {

        return ResponseEntity.ok(commentService.getBoardComments(boardId));
    }

    // 내 댓글 리스트 조회
    @GetMapping("/api/user/comment")
    public ResponseEntity<List<CommentResponse>> getMyComments(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok(commentService.getMyComments(userPrincipal.getId()));
    }

    // 댓글 등록
    @PostMapping("/api/board/{id}/comment")
    public ResponseEntity<CommentResponse> add(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                               @RequestBody CommentRequest commentRequest,
                                               @PathVariable(name = "id") Long boardId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.add(commentRequest, userPrincipal.getId(), boardId));
    }

    // 댓글 삭제
    @DeleteMapping("/api/board/{boardId}/comment/{commentId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                       @PathVariable(name = "commentId") Long commentId,
                                       @PathVariable(name = "boardId") Long boardId) {

        commentService.delete(userPrincipal.getId(), boardId, commentId);
        return ResponseEntity.noContent().build();
    }
}
