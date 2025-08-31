package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoffeeApiController {
    @Autowired
    private CoffeeService coffeeService;

    //POST
    @PostMapping("/api/coffees")
    public ResponseEntity<Coffee> create(@RequestBody CoffeeForm dto) {
        Coffee created = coffeeService.create(dto);
        return  (created != null)?
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //GET
    //전체 데이터 조회
    @GetMapping("/api/coffees")
    public List<Coffee> index() {
        return coffeeService.index();
    }

    //개별 데이터 조회
    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id) {
        return coffeeService.show(id);
    }

    //PATCH
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeForm dto) {
        Coffee updated = coffeeService.update(id, dto);

        return (updated != null) ?
            ResponseEntity.status(HttpStatus.OK).body(updated):
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee>  delete(@PathVariable Long id) {
        Coffee deleted = coffeeService.delete(id);
        return (deleted != null)?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //
    @PostMapping("/api/coffees/transaction")
    public ResponseEntity<List<Coffee>> transationTest(@RequestBody List<CoffeeForm> dtos) {
        List<Coffee> coffeeList = coffeeService.transactionTest(dtos);

        return (coffeeList != null)?
                ResponseEntity.status(HttpStatus.OK).body(coffeeList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
