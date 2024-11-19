package com.gugu.study.board.entity;

import com.gugu.study.board.dto.BoardRequest;
import com.gugu.study.comm.BaseEntity;
import com.gugu.study.auth.entity.User;
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
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //제목
    @Column(nullable = false)
    private String title;
    //내용
    @Column(nullable = false)
    private String content;
    //작성일
    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
    private LocalDateTime createDate;
    //수정일
    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    private LocalDateTime updatedDate;
    //작성자 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    public Board(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(BoardRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
