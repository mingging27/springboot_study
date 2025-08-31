package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String body;

    @JoinColumn(name = "article_id") //외래키 연결
    @ManyToOne //Comment 기준으로 Article과 다대일 관계
    private Article article;

    public static Comment createComment(CommentDto dto, Article article) {
        //예외
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패. 댓글의 id가 없어야 합니다.");
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패. 게시글의 id가 잘못되었습니다.");
        //반환
        return new Comment(dto.getId(), dto.getNickname(), dto.getBody(), article);
    }

    public void patch(CommentDto dto, Long commentId) {
        if (dto.getId() != commentId) throw new IllegalArgumentException("댓글 생성 실패. 댓글의 id가 잘못되었습니다.");

        if (dto.getNickname() != null) this.nickname = dto.getNickname();
        if (dto.getBody() != null) this.body = dto.getBody();
    }
}
