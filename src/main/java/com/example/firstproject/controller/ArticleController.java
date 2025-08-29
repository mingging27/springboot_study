package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ArticleController {
    @Autowired //스프링부트가 미리 생성해놓은 리파지터리 객체 주입
    private ArticleRepository articleRepository;    //스프링부트가 알아서 객체 생성

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")   //폼 데이터를 post 방식으로 전송해서 받을 때도 PostMapping으로
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
        // System.out.println(form.toString());

        // 1.DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        // System.out.println(article.toString());

        // 2.리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        // System.out.println(saved.toString());
        return "articles/new";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {  //@PathVariable: URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져옴
        log.info("id= "+id);
        // 1. id를 조회해 DB에서 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);  // id 값을 기준으로 데이터를 찾아 Optional<Article> 타입으로 반환
        //orElse(null): id 값으로 데이터를 찾을 때 해당 id 값이 없으면 null 반환

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }
}
