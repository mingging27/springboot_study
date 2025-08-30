package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleRepository articleRepository;

    //GET
    @GetMapping("api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    //POST
    @PostMapping("api/articles")
    public Article create(@RequestBody ArticleForm dto) {   //JSON 데이터를 받아와야 하므로 앞에 @RequestBody 어노테이션 추가
        //dto 내용 로그 남김
        log.info(dto.toString());

        Article articleEntity = dto.toEntity();
        log.info(articleEntity.toString());

        return articleRepository.save(articleEntity);
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        log.info(dto.toString());

        //dto->엔티티 변환 및 타겟 유무 확인
        Article articleEntity = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청(400) 처리, 타겟이 비어있거나, url 속 id와 엔티티 속 id가 불일치할 때
        if(target == null || id != articleEntity.getId()) {
            log.info("잘못된 요청! id:{}, article: {}", articleEntity.getId(), articleEntity.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //내용 수정 후 업데이트
        target.patch(articleEntity);
        Article updated = articleRepository.save(target);

        //정상 응답(200)
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> remove(@PathVariable Long id) {
        //대상 확인
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if(target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //삭제 후 정상 응답(200)
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
