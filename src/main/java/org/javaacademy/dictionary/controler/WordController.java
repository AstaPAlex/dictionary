package org.javaacademy.dictionary.controler;

import lombok.RequiredArgsConstructor;
import org.javaacademy.dictionary.dto.DescriptionDtoRq;
import org.javaacademy.dictionary.dto.PageWordDto;
import org.javaacademy.dictionary.dto.WordDtoRq;
import org.javaacademy.dictionary.dto.WordDtoRs;
import org.javaacademy.dictionary.service.WordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/word")
public class WordController {
    private final WordService wordService;

    @ResponseStatus(CREATED)
    @PostMapping
    public WordDtoRs createWord(@RequestBody WordDtoRq wordDtoRq) {
        return wordService.create(wordDtoRq);
    }

    @ResponseStatus(ACCEPTED)
    @GetMapping
    public List<WordDtoRs> getAllWords() {
        return wordService.getWords();
    }

    @ResponseStatus(ACCEPTED)
    @GetMapping("/{word}")
    public WordDtoRs getWord(@PathVariable String word) {
        return wordService.getWord(word);
    }

    @ResponseStatus(ACCEPTED)
    @PatchMapping("/{word}")
    public WordDtoRs updateWord(@PathVariable String word, @RequestBody DescriptionDtoRq descriptionDtoRq) {
        return wordService.update(word, descriptionDtoRq);
    }

    @ResponseStatus(ACCEPTED)
    @DeleteMapping("/{word}")
    public void deleteWord(@PathVariable String word) {
        wordService.deleteWord(word);
    }

    @ResponseStatus(ACCEPTED)
    @GetMapping("/page")
    public PageWordDto<List<WordDtoRs>> getWords(@RequestParam Integer startElement,
                                                 @RequestParam Integer pageSize,
                                                 @RequestParam(required = false) boolean refresh) {
        return wordService.getWordsPage(startElement, pageSize, refresh);
    }

}
