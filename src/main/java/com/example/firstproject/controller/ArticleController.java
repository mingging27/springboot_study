package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired //스프링부트가 미리 생성해놓은 리파지터리 객체 주입
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;//스프링부트가 알아서 객체 생성

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
        return "redirect:/articles/"+saved.getId(); //리다이렉트 정의
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {  //@PathVariable: URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져옴
        log.info("id= "+id);
        // 1. id를 조회해 DB에서 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);  // id 값을 기준으로 데이터를 찾아 Optional<Article> 타입으로 반환
        //orElse(null): id 값으로 데이터를 찾을 때 해당 id 값이 없으면 null 반환
        List<CommentDto> commentDtos = commentService.comments(id);


        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. DB에서 모든 Article 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();  //Iterable 타입의 데이터 반환

        // 2. 가져온 Article 묶음 모델에 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 사용자에게 보여 줄 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article article = articleRepository.findById(id).orElse(null);

        //모델에 추가하기
        model.addAttribute("article", article);

        // 뷰 페이지 반환하기
        return "/articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {    //DTO를 매개변수로 받아옴
        log.info(form.toString());

        //DTO -> 엔티티 변환
        Article article = form.toEntity();
        log.info(article.toString());

        //DB에서 원본 가져오기 - 원본이 존재하는지 판단 -> 비정상적인 수정 방지 (악의적으로 잘못된 id를 입력하는 경우)
        Article target = articleRepository.findById(article.getId()).orElse(null);

        //값 갱신하기
        if (target != null) {
            articleRepository.save(article);
        }
        return "redirect:/articles/"+article.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!");

        //1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        //2. 대상 엔티티 삭제하기
        if(target != null) {
            articleRepository.delete(target);
            //msg가 객체인 건가...?
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        //3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
