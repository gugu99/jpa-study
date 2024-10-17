package com.gugu.study.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gugu.study.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private Long userId;
    private Long boardId;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();;
        this.content = comment.getContent();;
        this.userId = comment.getUser().getId();
        this.boardId = comment.getBoard().getId();
        this.name = comment.getUser().getName();
        this.createdAt = comment.getCreateDate();
        this.updatedAt = comment.getUpdatedDate();
    }
}
