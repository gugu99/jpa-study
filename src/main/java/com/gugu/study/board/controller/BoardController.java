package com.gugu.study.board.controller;

import com.gugu.study.board.dto.BoardRequest;
import com.gugu.study.board.dto.BoardResponse;
import com.gugu.study.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글판 리스트 조회
    @GetMapping("/api/board")
    public ResponseEntity<List<BoardResponse>> getBoards(@AuthenticationPrincipal UserDetails userDetails){
        Long userId = Long.parseLong(userDetails.getUsername());

        return ResponseEntity.ok().body(boardService.getBoards(userId));
    }

    // 내 게시글 리스트 조회
    @GetMapping("/api/user/board")
    public ResponseEntity<List<BoardResponse>> getMyBoards(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());

        return ResponseEntity.ok().body(boardService.getMyBoards(userId));
    }

    // 게시글 상세 조회
    @GetMapping("/api/board/{id}")
    public ResponseEntity<BoardResponse> getOne(@AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable(name = "id") Long boardId) {
        Long userId = Long.parseLong(userDetails.getUsername());

        return ResponseEntity.ok(boardService.getOne(userId, boardId));
    }

    // 게시글 등록
    @PostMapping("/api/board")
    public ResponseEntity<BoardResponse> add(@RequestBody BoardRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails){
        Long userId = Long.parseLong(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boardService.add(request, userId));
    }

    // 게시글 수정

    // 게시글 삭제
}
