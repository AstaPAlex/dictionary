package org.javaacademy.dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.dictionary.dto.PageWordDto;
import org.javaacademy.dictionary.dto.WordDtoRq;
import org.javaacademy.dictionary.dto.WordDtoRs;
import org.javaacademy.dictionary.entity.Word;
import org.javaacademy.dictionary.repository.WordAlreadyExistException;
import org.javaacademy.dictionary.repository.WordNotFoundException;
import org.javaacademy.dictionary.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WordService {
    private final WordRepository repository;

    public WordDtoRs create(WordDtoRq wordDtoRq) throws WordEmptyException, WordAlreadyExistException {
        Word wordEntity = convertToEntity(wordDtoRq);
        repository.add(wordEntity);
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

    @SneakyThrows
    public List<WordDtoRs> getWords() {
        Thread.sleep(3000);
        return repository.getWords().values().stream()
                .map(this::convertToDtoRs)
                .toList();
    }

    @SneakyThrows
    public WordDtoRs getWord(String wordFind) throws WordNotFoundException {
        Thread.sleep(3000);
        return repository.findWord(wordFind.toUpperCase())
                .map(this::convertToDtoRs)
                .orElseThrow(WordNotFoundException::new);
    }

    public void update(String word, WordDtoRq wordDtoRq) throws WordEmptyException,
            WordNotFoundException {
        repository.update(word.toUpperCase(), convertToEntity(wordDtoRq));
    }

    public boolean deleteWord(String word) {
        return repository.delete(word);
    }

    public PageWordDto<List<WordDtoRs>> getWordsPage(Integer startElement, Integer pageSize) {
        List<WordDtoRs> wordsDtoRs = repository.getWords().values().stream()
                .skip(startElement)
                .limit(pageSize)
                .map(this::convertToDtoRs)
                .toList();
        return new PageWordDto<>(
                wordsDtoRs.size(),
                repository.getWords().size(),
                startElement,
                startElement + wordsDtoRs.size(),
                wordsDtoRs);
    }
}
