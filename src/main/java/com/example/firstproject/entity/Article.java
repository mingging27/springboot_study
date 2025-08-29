package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    @GeneratedValue //대푯값인 id를 자동으로 생성
    private Long id;
    @Column //각각 선언
    private String title;
    @Column
    private String content;

//    public Long getId() {
//        return this.id;
//    }
}
