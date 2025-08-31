package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.swing.text.html.parser.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id;
    private String nickname;
    private String body;
    @JsonProperty("article_id")
    private Long articleId;

    public static CommentDto createCommentDto(Comment c) {
        return new CommentDto(c.getId(), c.getNickname(), c.getBody(), c.getArticle().getId());

    }

}
