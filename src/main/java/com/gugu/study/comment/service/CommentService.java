package com.gugu.study.comment.service;

import com.gugu.study.board.entity.Board;
import com.gugu.study.board.repository.BoardRepository;
import com.gugu.study.comment.dto.CommentRequest;
import com.gugu.study.comment.dto.CommentResponse;
import com.gugu.study.comment.entity.Comment;
import com.gugu.study.comment.repository.CommentRepository;
import com.gugu.study.user.entity.User;
import com.gugu.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 특정 게시글 댓글 리스트 조회

    // 댓글 등록
    @Transactional
    public CommentResponse add(CommentRequest commentRequest, Long userId, Long boardId) {
        // user 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("잘못된 회원정보입니다."));

        // 게시글 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 댓글 저장
        Comment comment = commentRepository.save(
                Comment.builder()
                        .user(user)
                        .board(board)
                        .content(commentRequest.getContent())
                        .build()
        );
        return new CommentResponse(comment);
    }

    // 댓글 수정

    // 댓글 삭제
}
