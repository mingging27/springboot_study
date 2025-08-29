package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {
    @Autowired //스프링부트가 미리 생성해놓은 리파지터리 객체 주입
    private MemberRepository memberRepository;    //스프링부트가 알아서 객체 생성

    @GetMapping("/members/new")
    public String newMembersForm() {
        return "members/new";
    }

    @PostMapping("/join")   //폼 데이터를 post 방식으로 전송해서 받을 때도 PostMapping으로
    public String createMemers(MemberForm form) {
        log.info(form.toString());

        // 1.DTO를 엔티티로 변환
        Member member = form.toEntity();
        log.info(member.toString());

        // 2.리파지터리로 엔티티를 DB에 저장
        Member saved = memberRepository.save(member);
        log.info(saved.toString());

        return "members/new";
    }
}
