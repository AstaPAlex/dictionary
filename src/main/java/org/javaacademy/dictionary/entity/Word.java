package org.javaacademy.dictionary.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class Word implements Comparable<Word> {
    @NonNull
    private final String word;
    @NonNull
    private String description;

    @Override
    public int compareTo(Word word) {
        return this.word.compareTo(word.getWord());
    }
}
