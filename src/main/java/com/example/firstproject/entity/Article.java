package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter //get~ 메서드: 외부에서 객체의 데이터를 읽을 때 사용
@AllArgsConstructor
@NoArgsConstructor  //기본 생성자 코드 추가 (JPA에서 필수)
@ToString
public class Article {
    @Id //대푯값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id 자동 생성 전략
    private Long id;
    @Column //각각 선언
    private String title;
    @Column
    private String content;

    public void patch(Article article) {
        if (article.title != null)
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }

//    public Long getId() {
//        return this.id;
//    }
}
