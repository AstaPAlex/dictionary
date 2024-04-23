package org.javaacademy.dictionary.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.dictionary.dto.WordDtoRq;
import org.javaacademy.dictionary.dto.WordDtoRs;
import org.javaacademy.dictionary.entity.Word;
import org.javaacademy.dictionary.repository.WordRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WordService {
    private final WordRepository repository;

    public WordDtoRs create(WordDtoRq wordDtoRq) throws WordEmptyException {
        Word wordEntity = convertToEntity(wordDtoRq);
        repository.add(wordEntity);
        return convertToDtoRs(wordEntity);
    }

    private Word convertToEntity(WordDtoRq wordDtoRq) throws WordEmptyException {
        if (wordDtoRq.getWord() == null || wordDtoRq.getDescription() == null) {
            throw new WordEmptyException("Не все поля переданы!");
        }
        if (wordDtoRq.getWord().isEmpty() || wordDtoRq.getDescription().isEmpty()) {
            throw new WordEmptyException("Не все поля заполнены!");
        }
        return new Word(wordDtoRq.getWord(), wordDtoRq.getDescription());
    }

    private WordDtoRs convertToDtoRs(Word word) {
        return new WordDtoRs(word.getWord(), word.getDescription());
    }
}
