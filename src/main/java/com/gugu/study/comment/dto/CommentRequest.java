package com.gugu.study.comment.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentRequest {
    private String content; // 댓글 내용
}
