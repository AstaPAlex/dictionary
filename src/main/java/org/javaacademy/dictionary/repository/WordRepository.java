package org.javaacademy.dictionary.repository;

import org.javaacademy.dictionary.entity.Word;
import org.springframework.stereotype.Component;

import java.util.TreeSet;

@Component
public class WordRepository {
    TreeSet<Word> words = new TreeSet<>();

    public void add(Word word) {
        words.add(word);
    }



}
