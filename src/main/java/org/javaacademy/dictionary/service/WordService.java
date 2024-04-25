package org.javaacademy.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.dictionary.dto.DescriptionDtoRq;
import org.javaacademy.dictionary.dto.PageWordDto;
import org.javaacademy.dictionary.dto.WordDtoRq;
import org.javaacademy.dictionary.dto.WordDtoRs;
import org.javaacademy.dictionary.entity.Word;
import org.javaacademy.dictionary.repository.exception.WordNotFoundException;
import org.javaacademy.dictionary.repository.WordRepository;
import org.javaacademy.dictionary.service.exception.NotComplyFillingRulesException;
import org.javaacademy.dictionary.service.exception.WordEmptyException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WordService {
    private final WordRepository wordRepository;

    @CacheEvict(cacheNames = {"getAll", "getWord", "getWordPage"}, allEntries = true)
    public WordDtoRs create(WordDtoRq wordDtoRq) {
        checkOnRules(wordDtoRq.getWord(), wordDtoRq.getDescription());
        Word wordEntity = convertToEntity(wordDtoRq);
        wordRepository.add(wordEntity);
        return convertToDtoRs(wordEntity);
    }

    private Word convertToEntity(WordDtoRq wordDtoRq) {
        return new Word(wordDtoRq.getWord().toUpperCase(), wordDtoRq.getDescription());
    }

    private boolean checkOnRules(String word, String description) {
        if (word == null || description == null
                || word.isEmpty() || description.isEmpty()) {
            throw new WordEmptyException();
        }
        if (!word.matches("^[a-zA-Z]+$") || !description.matches("^[?!,.а-яА-ЯёЁ0-9\\s]+$")) {
            throw new NotComplyFillingRulesException();
        }
        return true;
    }

    private WordDtoRs convertToDtoRs(Word word) {
        return new WordDtoRs(word.getWord(), word.getDescription());
    }

    @Cacheable(cacheNames = "getAll")
    @SneakyThrows
    public List<WordDtoRs> getWords() {
        Thread.sleep(3000);
        return wordRepository.getWords().values().stream()
                .map(this::convertToDtoRs)
                .toList();
    }

    @SneakyThrows
    @Cacheable(cacheNames = "getWord")
    public WordDtoRs getWord(String wordFind) {
        Thread.sleep(3000);
        return wordRepository.findWord(wordFind.toUpperCase())
                .map(this::convertToDtoRs)
                .orElseThrow(WordNotFoundException::new);
    }

    @CacheEvict(cacheNames = {"getAll", "getWord", "getWordPage"}, allEntries = true)
    public WordDtoRs update(String word, DescriptionDtoRq descriptionDtoRq) {
        String textUpdate = descriptionDtoRq.getDescription();
        checkOnRules(word, textUpdate);
        Word updateWord = wordRepository.update(word.toUpperCase(), textUpdate);
        return convertToDtoRs(updateWord);
    }

    @CacheEvict(cacheNames = {"getAll", "getWord", "getWordPage"}, allEntries = true)
    public void deleteWord(String word) {
        wordRepository.delete(word);
    }

    @SneakyThrows
    @Cacheable(cacheNames = "getWordPage")
    @CachePut(cacheNames = "getWordPage", condition = "#refresh == true")
    public PageWordDto<List<WordDtoRs>> getWordsPage(Integer startElement, Integer pageSize, boolean refresh) {
        Thread.sleep(3000);
        List<WordDtoRs> wordsDtoRs = wordRepository.getWords().values().stream()
                .skip(startElement)
                .limit(pageSize)
                .map(this::convertToDtoRs)
                .toList();
        return new PageWordDto<>(
                wordsDtoRs.size(),
                wordRepository.getWords().size(),
                startElement,
                startElement + wordsDtoRs.size() - 1,
                wordsDtoRs);
    }
}
