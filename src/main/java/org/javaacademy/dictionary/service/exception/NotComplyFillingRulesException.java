package org.javaacademy.dictionary.service.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Ошибка заполнения!Слово на английском,"
        + " описание на русском языке + знаки препинания!")
public class NotComplyFillingRulesException extends RuntimeException{
}
