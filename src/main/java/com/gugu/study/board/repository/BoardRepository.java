package com.gugu.study.board.repository;

import com.gugu.study.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUser_Id(Long userId);
}
