package com.gugu.study.board.controller;

import com.gugu.study.board.dto.BoardRequest;
import com.gugu.study.board.dto.BoardResponse;
import com.gugu.study.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 글쓰기
    @PostMapping("/api/board")
    public ResponseEntity<BoardResponse> add(@RequestBody BoardRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails){
        Long userId = Long.parseLong(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boardService.add(request, userId));
    }
}
