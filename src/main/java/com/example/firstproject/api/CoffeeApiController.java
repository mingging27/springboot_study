package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController //어노테이션 다름
public class CoffeeApiController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    //POST
    @PostMapping("/api/coffees")
    public Coffee create(@RequestBody CoffeeForm dto) {
        //dto->엔티티
        Coffee coffee = dto.toEntity();
        log.info(coffee.toString());

        //저장한 엔티티 값 리턴
        return coffeeRepository.save(coffee);
    }

    //GET
    //전체 데이터 조회
    @GetMapping("/api/coffees")
    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    //개별 데이터 조회
    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    //PATCH
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody CoffeeForm dto) {
        //dto->엔티티
        Coffee coffee = dto.toEntity();

        //기존 데이터 조회
        Coffee target = coffeeRepository.findById(id).orElse(null);

        //잘못된 접근인지 확인
        if (target == null | id != coffee.getId()) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}

        //수정
        target.patch(coffee);
        coffeeRepository.save(target);

        return ResponseEntity.status(HttpStatus.OK).body(target);
    }

    //DELETE
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        //기존 데이터 가져오기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        //기존 데이터 유무 확인
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //삭제 후 상태 코드 리턴
        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
