package org.javaacademy.dictionary.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class WordDtoRq {
    private String word;
    private String description;
}
