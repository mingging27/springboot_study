package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

        return "redirect:/members/"+saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id="+id);

        Member member = memberRepository.findById(id).orElse(null);

        model.addAttribute("member", member);

        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {
        List memberList = memberRepository.findAll();

        model.addAttribute("memberList", memberList);

        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "/members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form) {
        log.info(form.toString());
        Member member = form.toEntity();

        Member target = memberRepository.findById(member.getId()).orElse(null);

        if (target != null) {
            memberRepository.save(member);
        }

        return "redirect:/members/"+member.getId();
    }
}
