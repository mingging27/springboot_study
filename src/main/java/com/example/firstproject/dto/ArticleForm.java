package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //생성자 자동 생성
@ToString   //toString() 메소드와 동일 효과

public class ArticleForm {
    private Long id;    //edit을 위한 아이디 필드 추가
    private String title;
    private String content;

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
