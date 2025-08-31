package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; //앞으로 사용할 수 있는 패키지 미리 임포트

@SpringBootTest //스프링부트와 연동해 통합 테스트 수행
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test   //테스트를 위한 코드임
    void index() {
        //예상 데이터
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");

        // 여기서 생성되는 expected 리스트는 정적 리스트로, add()나 remove() 메서드 사용 불가
        // 수정하기 위해서는 동적 리스트로 전환해야함 new ArrayList<>(expected)
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        //실제 데이터
        List<Article> articles = articleService.index();

        //비교 및 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        //예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        //실제 데이터
        Article article = articleService.show(id);

        //비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패_존재하지_않는_id_입력() {
        //예상 데이터
        Long id = -1L;
        Article expected = null;

        //실제 데이터
        Article article = articleService.show(id);

        //비교 및 검증
        assertEquals(expected, article);
    }

    //전체 테스트 시, create의 성공으로 인해 DB에 새로운 data를 추가하기 때문에 데이터가 오염되어 index 테스트가 제대로 작동하지 X -> create_성공() 메소드를 트랜젝션 처리하여, 테스트가 끝나면 롤백 시켜야함
    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        //예상 데이터
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        //실제 데이터
        Article article = articleService.create(dto);

        //비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void create_실패_id가_포함된_dto_입력() {
        //예상 데이터
        Long id = 1L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null; //서비스 테스트니까!!!

        //실제 데이터
        Article article = articleService.create(dto);

        //비교 및 검증
        assertEquals(expected, article);
    }

    @Transactional
    @Test
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {
        //예상
        Long urlId = 1L;
        Long id = 1L;
        String title = "아아아아";
        String content = "수정할내용";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);

        //실제
        Article article = articleService.update(urlId, dto);

        //비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test
    void update_성공_존재하는_id와_title만_있는_dto_입력() {
        //예상
        Long urlId = 1L;
        Long id = 1L;
        String title = "아아아아";
        String content = null;
        String row_content = "1111";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, row_content);

        //실제
        Article article = articleService.update(urlId, dto);

        //비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test
    void update_실패_존재하지_않는_id의_dto_입력() {
        //예상
        Long urlId = -1L;
        Long id = -1L;
        String title = "아아아아";
        String content = "수정할내용";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        //실제
        Article article = articleService.update(urlId, dto);

        //비교 및 검증
        assertEquals(expected, article);
    }


    @Transactional
    @Test
    void delete_성공_존재하는_id_입력() {
        //예상
        Long id = 1L;
        String row_title = "가가가가";
        String row_content = "1111";

        Article expected = new Article(id, row_title, row_content);

        //실제
        Article article = articleService.delete(id);

        //비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test
    void delete_실패_존재하지_않는_id_입력() {
        //예상
        Long id = -1L;

        Article expected = null;

        //실제
        Article article = articleService.delete(id);

        //비교 및 검증
        assertEquals(expected, article);
    }
}