package com.gugu.study.board.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardRequest {
    private String boardTitle;
    private String boardContent;
}
