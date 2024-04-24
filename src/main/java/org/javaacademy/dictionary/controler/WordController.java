package org.javaacademy.dictionary.controler;

import lombok.RequiredArgsConstructor;
import org.javaacademy.dictionary.dto.PageWordDto;
import org.javaacademy.dictionary.dto.WordDtoRq;
import org.javaacademy.dictionary.dto.WordDtoRs;
import org.javaacademy.dictionary.repository.WordAlreadyExistException;
import org.javaacademy.dictionary.repository.WordNotFoundException;
import org.javaacademy.dictionary.service.WordEmptyException;
import org.javaacademy.dictionary.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/word")
public class WordController {
    private final WordService service;

    @PostMapping
    public ResponseEntity<?> createWord(@RequestBody WordDtoRq wordDtoRq) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(wordDtoRq));
        } catch (WordEmptyException | WordAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка заполнения:\n" + e.getMessage());
        }
    }

    @GetMapping
    public List<WordDtoRs> getAllWords() {
        return service.getWords();
    }

    @GetMapping("/{word}")
    public ResponseEntity<?> getWord(@PathVariable String word) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(service.getWord(word));
        } catch (WordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{word}")
    public ResponseEntity<?> updateWord(@PathVariable String word, @RequestBody WordDtoRq wordDtoRq) {
        try {
            service.update(word, wordDtoRq);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (WordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (WordEmptyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка заполнения:\n" + e.getMessage());
        }
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<?> deleteWord(@PathVariable String word) {
        return service.deleteWord(word)
                ? ResponseEntity.status(HttpStatus.ACCEPTED).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/page")
    public PageWordDto<List<WordDtoRs>> getWords(@RequestParam Integer startElement,
                                                 @RequestParam Integer pageSize) {
        return service.getWordsPage(startElement, pageSize);
    }

}
