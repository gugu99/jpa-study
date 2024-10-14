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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게시판 리스트 조회
    public List<BoardResponse> getBoards(Long userId) {
        // user 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("잘못된 회원정보입니다."));

        // 리스트 조회
        return boardRepository.findAll().stream().map(BoardResponse::new).toList();
    }

    // 내 게시글 리스트 조회
    public List<BoardResponse> getMyBoards(Long userId){
        return boardRepository.findByUser_Id(userId).stream().map(BoardResponse::new).toList();
    }

    // 게시글 상세 조회
    public BoardResponse getOne(Long userId, Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 게시글 조회
        return new BoardResponse(board);
    }

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

    // 게시글 수정

    // 게시글 삭제
}
