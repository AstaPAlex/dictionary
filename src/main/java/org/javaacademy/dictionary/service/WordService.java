package org.javaacademy.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.dictionary.dto.PageWordDto;
import org.javaacademy.dictionary.dto.WordDtoRq;
import org.javaacademy.dictionary.dto.WordDtoRs;
import org.javaacademy.dictionary.entity.Word;
import org.javaacademy.dictionary.repository.exception.WordAlreadyExistException;
import org.javaacademy.dictionary.repository.exception.WordNotFoundException;
import org.javaacademy.dictionary.repository.WordRepository;
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
    public WordDtoRs create(WordDtoRq wordDtoRq) throws WordEmptyException, WordAlreadyExistException {
        Word wordEntity = convertToEntity(wordDtoRq);
        wordRepository.add(wordEntity);
        return convertToDtoRs(wordEntity);
    }

    private Word convertToEntity(WordDtoRq wordDtoRq) throws WordEmptyException {
        if (wordDtoRq.getWord() == null || wordDtoRq.getDescription() == null) {
            throw new WordEmptyException("Не все поля отправлены!");
        }
        if (wordDtoRq.getWord().isEmpty() || wordDtoRq.getDescription().isEmpty()) {
            throw new WordEmptyException("Не все поля заполнены!");
        }
        return new Word(wordDtoRq.getWord().toUpperCase(), wordDtoRq.getDescription());
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
    public WordDtoRs getWord(String wordFind) throws WordNotFoundException {
        Thread.sleep(3000);
        return wordRepository.findWord(wordFind.toUpperCase())
                .map(this::convertToDtoRs)
                .orElseThrow(WordNotFoundException::new);
    }

    @CacheEvict(cacheNames = {"getAll", "getWord", "getWordPage"}, allEntries = true)
    public void update(String word, WordDtoRq wordDtoRq) throws WordEmptyException,
            WordNotFoundException {
        wordRepository.update(word.toUpperCase(), convertToEntity(wordDtoRq));
    }

    @CacheEvict(cacheNames = {"getAll", "getWord", "getWordPage"}, allEntries = true)
    public boolean deleteWord(String word) {
        return wordRepository.delete(word);
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
                startElement + wordsDtoRs.size(),
                wordsDtoRs);
    }
}
