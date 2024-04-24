package org.javaacademy.dictionary.repository.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Такое слово уже существует!")
public class WordAlreadyExistException extends RuntimeException{
}
