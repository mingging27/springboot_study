package com.example.firstproject.repository;

import com.example.firstproject.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

// 생성한 엔티티 객체를 DB에 넣는 등 DB와 상호작용
public interface MemberRepository extends CrudRepository<Member, Long> {
    @Override
    ArrayList<Member> findAll();
}
