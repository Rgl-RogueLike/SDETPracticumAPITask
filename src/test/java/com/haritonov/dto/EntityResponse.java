package com.haritonov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Объект Data Transfer Object (DTO) ответа, содержащий данные сущности.
 * Возвращается сервером при успешном выполнении операций Get, Create.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityResponse {

    /**
     * Уникальный идентификатор сущности.
     */
    private Integer id;

    /**
     * Заголовок сущности.
     */
    private String title;

    /**
     * Статус верификации сущности.
     */
    private Boolean verified;

    /**
     * Объект, содержащий дополнительные данные.
     */
    private AdditionResponse addition;

    /**
     * Список важных чисел, связанных с сущностью.
     * Соответствует полю {@code important_numbers} в JSON.
     */
    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers;
}
