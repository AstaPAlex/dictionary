package org.javaacademy.dictionary.repository.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Слово не найдено!")
public class WordNotFoundException extends RuntimeException {
}
