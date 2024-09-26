package com.gugu.study.board.entity;

import com.gugu.study.board.dto.BoardRequest;
import com.gugu.study.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    //제목
    @Column(nullable = false)
    private String boardTitle;
    //내용
    @Column(nullable = false)
    private String boardContent;
    //작성일
    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //수정일
    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private String updatedAt;
    //작성자 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public void update(BoardRequest request) {
        this.boardTitle = request.getBoardTitle();
        this.boardContent = request.getBoardContent();
    }
}
