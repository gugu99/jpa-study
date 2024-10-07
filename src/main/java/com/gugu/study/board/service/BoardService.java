package com.gugu.study.board.service;

import com.gugu.study.board.dto.BoardRequest;
import com.gugu.study.board.dto.BoardResponse;
import com.gugu.study.board.entity.Board;
import com.gugu.study.board.repository.BoardRepository;
import com.gugu.study.user.entity.User;
import com.gugu.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게시글 등록
    @Transactional
    public BoardResponse add(BoardRequest boardRequest, Long userId){

        // user 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("잘못된 회원정보입니다."));

        // 게시글 저장
        Board board = boardRepository.save(
                Board.builder()
                        .user(user)
                        .title(boardRequest.getTitle())
                        .content(boardRequest.getContent())
                        .build()
        );

        return new BoardResponse(board);
    }
}
