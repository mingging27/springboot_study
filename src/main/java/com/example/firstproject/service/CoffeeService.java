package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    //POST
    public Coffee create(CoffeeForm dto) {
        //dto->엔티티
        Coffee coffee = dto.toEntity();

        if (coffee.getId() != null) return null;

        //저장한 엔티티 값 리턴
        return coffeeRepository.save(coffee);
    }

    //GET
    //전체 데이터 조회
    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    //개별 데이터 조회
    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);

    }

    //PATCH
    public Coffee update(Long id, CoffeeForm dto) {
        //dto->엔티티
        Coffee coffee = dto.toEntity();

        //기존 데이터 조회
        Coffee target = coffeeRepository.findById(id).orElse(null);

        //잘못된 접근인지 확인 후 잘못된 요청(400) 반환
        if (target == null | id != coffee.getId()) return null;

        //수정 후 정상 응답(200) 리턴
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);

        return updated;
    }

    //DELETE
    public Coffee delete(Long id) {
        //기존 데이터 가져오기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        //기존 데이터 유무 확인
        if (target == null) {
            return null;
        }

        //삭제 후 상태 코드 리턴
        coffeeRepository.delete(target);
        return target;
    }

    //트랜젝션
    @Transactional
    public List<Coffee> transactionTest(List<CoffeeForm> dtos) {
        // dtos -> Entitys
        List<Coffee> coffees = dtos.stream()
                .map(dto -> dto.toEntity() )
                .collect(Collectors.toList());

        // DB에 저장
        coffees.stream()
                .forEach(coffee -> coffeeRepository.save(coffee));

        // 예외 발생
        Coffee target = coffeeRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("예외 발생"));

        return coffees;
    }
}
