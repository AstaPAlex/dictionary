package org.javaacademy.dictionary.repository;

import lombok.Getter;
import org.javaacademy.dictionary.entity.Word;
import org.javaacademy.dictionary.repository.exception.WordAlreadyExistException;
import org.javaacademy.dictionary.repository.exception.WordNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.TreeMap;

@Component
@Getter
public class WordRepository {
    TreeMap<String, Word> words = new TreeMap<>();

    public void add(Word word) {
        if (words.containsKey(word.getWord())) {
            throw new WordAlreadyExistException("Слово уже есть в словаре!");
        }
        words.put(word.getWord(), word);
    }

    public Optional<Word> findWord(String wordFind) {
        return Optional.ofNullable(words.get(wordFind));
    }

    public Word update(String wordKey, String description) {
        if (!words.containsKey(wordKey)) {
            throw new WordNotFoundException();
        }
        Word word = words.get(wordKey);
        word.setDescription(description);
        return word;
    }

    public void delete(String word) {
        if (words.remove(word) == null) {
            throw new WordNotFoundException();
        }
    }

}
