package org.javaacademy.dictionary.service.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Не все поля заполнены!")
public class WordEmptyException extends RuntimeException {
}
