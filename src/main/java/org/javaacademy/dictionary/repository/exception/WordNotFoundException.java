package org.javaacademy.dictionary.repository.exception;

import lombok.experimental.StandardException;

@StandardException
public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException() {
        super("Слово не найдено!");
    }
}
