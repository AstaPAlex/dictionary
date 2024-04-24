package org.javaacademy.dictionary.repository;

import lombok.Getter;
import lombok.SneakyThrows;
import org.javaacademy.dictionary.entity.Word;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.TreeMap;

@Component
@Getter
public class WordRepository {
    TreeMap<String, Word> words = new TreeMap<>();

    public void add(Word word) throws WordAlreadyExistException {
        if (words.containsKey(word.getWord())) {
            throw new WordAlreadyExistException("Слово уже есть в словаре!");
        }
        words.put(word.getWord(), word);
    }

    @SneakyThrows
    public Optional<Word> findWord(String wordFind) {
        return Optional.ofNullable(words.get(wordFind));
    }

    public void update(String wordKey, Word word) throws WordNotFoundException {
        if (!words.containsKey(wordKey)) {
            throw new WordNotFoundException();
        }
        words.put(wordKey, word);
    }


    public boolean delete(String word) {
        return words.remove(word) != null;
    }

}
