package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 객체 생성 어노테이션
public class ArticleService {
    //서비스: 요청 처리
    @Autowired
    private ArticleRepository articleRepository;

    //GET
    //전체 조회
    public List<Article> index() {
        return articleRepository.findAll();
    }

    //개별 조회
    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article articleEntity = dto.toEntity();
        log.info("Article Entity: "+articleEntity.toString());

        if (articleEntity.getId() != null) return null;

        return articleRepository.save(articleEntity);
    }

    public Article update(Long id, ArticleForm dto) {
        log.info(dto.toString());

        //dto->엔티티 변환 및 타겟 유무 확인
        Article articleEntity = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청(400) 처리, 타겟이 비어있거나, url 속 id와 엔티티 속 id가 불일치할 때
        if(target == null || id != articleEntity.getId()) {
            log.info("잘못된 요청! id:{}, article: {}", articleEntity.getId(), articleEntity.toString());
            return null;
        }

        //내용 수정 후 업데이트
        target.patch(articleEntity);
        Article updated = articleRepository.save(target);

        return updated;
    }

    public Article delete(Long id) {
        //대상 확인
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if(target == null) {
            return null;
        }
        //삭제 후 정상 응답(200)
        articleRepository.delete(target);

        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dto 묶음을 엔티티로 변환하기 (dtos 스트림(리스트 등의 자료구조에서 저장된 요소를 하나씩 순회하며 처리)화)
        List<Article> articleList = dtos.stream()
                //dto가 하나하나 올 때마다 dto.toEntity 수행하여 매핑
                .map(dto -> dto.toEntity())
                //결과물(엔티티)을 리스트로 묶음
                .collect(Collectors.toList());

        //2. 엔티티 묶음을 DB에 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        //3. 강제 예외 발생시키기
        articleRepository.findById(-1L) //L이 붙는 이유: 리터럴 (기본이 int인데 이건 long타입이니까)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패"));

        //4.결과 값 반환하기
        return articleList;
    }
}
