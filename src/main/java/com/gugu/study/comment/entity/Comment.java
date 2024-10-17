package com.gugu.study.comment.entity;

import com.gugu.study.board.entity.Board;
import com.gugu.study.comm.BaseEntity;
import com.gugu.study.comment.dto.CommentRequest;
import com.gugu.study.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용
    @Column(nullable = false)
    private String content;
    // 작성일
    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
    private LocalDateTime createDate;
    // 수정일
    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    private LocalDateTime updatedDate;
    // 작성자 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    // 게시글 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    @Builder
    public Comment(String content, User user, Board board) {
        this.content = content;
        this.user = user;
        this.board = board;
    }

    public void update(CommentRequest request) {
        this.content = request.getContent();
    }
}
