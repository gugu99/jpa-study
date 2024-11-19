package com.gugu.study.board.controller;

import com.gugu.study.board.dto.BoardRequest;
import com.gugu.study.board.dto.BoardResponse;
import com.gugu.study.board.service.BoardService;
import com.gugu.study.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글판 리스트 조회
    @GetMapping("/api/board")
    public ResponseEntity<List<BoardResponse>> getBoards(@AuthenticationPrincipal UserPrincipal userPrincipal){

        return ResponseEntity.ok().body(boardService.getBoards(userPrincipal.getId()));
    }

    // 내 게시글 리스트 조회
    @GetMapping("/api/user/board")
    public ResponseEntity<List<BoardResponse>> getMyBoards(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok().body(boardService.getMyBoards(userPrincipal.getId()));
    }

    // 게시글 상세 조회
    @GetMapping("/api/board/{id}")
    public ResponseEntity<BoardResponse> getOne(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @PathVariable(name = "id") Long boardId) {

        return ResponseEntity.ok(boardService.getOne(userPrincipal.getId(), boardId));
    }

    // 게시글 등록
    @PostMapping("/api/board")
    public ResponseEntity<BoardResponse> add(@RequestBody BoardRequest request,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boardService.add(request, userPrincipal.getId()));
    }

    // 게시글 수정
    @PutMapping("/api/board/{id}")
    public ResponseEntity<BoardResponse> update(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @PathVariable(name = "id") Long boardId,
                                                @RequestBody BoardRequest boardRequest){

        return ResponseEntity.ok(boardService.update(userPrincipal.getId(), boardId, boardRequest));
    }

    // 게시글 삭제
    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                       @PathVariable(name = "id") Long boardId) {

        boardService.delete(userPrincipal.getId(), boardId);
        return ResponseEntity.noContent().build();
    }
}
