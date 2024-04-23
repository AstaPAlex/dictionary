package org.javaacademy.dictionary.controler;

import lombok.RequiredArgsConstructor;
import org.javaacademy.dictionary.dto.WordDtoRq;
import org.javaacademy.dictionary.service.WordEmptyException;
import org.javaacademy.dictionary.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/word")
public class WordController {
    private final WordService service;

    @PostMapping
    public ResponseEntity<?> createWord(@RequestBody WordDtoRq wordDtoRq) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(wordDtoRq));
        } catch (WordEmptyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка заполнения:\n" + e.getMessage());
        }
    }

}
