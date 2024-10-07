package com.gugu.study.board.dto;

import com.gugu.study.board.entity.Board;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardRequest {
    private String title; // 제목
    private String content; // 내용

}
