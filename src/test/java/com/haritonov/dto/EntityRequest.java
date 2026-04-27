package com.haritonov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Объект Data Transfer Object (DTO) для создания или обновления сущности.
 * Используется в теле запроса методов {@code POST /api/create} и {@code PATCH /api/patch}.
 */
@Builder
@Value
public class EntityRequest {

    /**
     * Заголовок сущности.
     */
    String title;

    /**
     * Статус верификации сущности.
     */
    boolean verified;

    /**
     * Объект, содержащий дополнительные данные.
     * Является вложенным JSON-объектом.
     */
    AdditionRequest addition;

    /**
     * Список важных чисел, связанных с сущностью.
     * Соответствует полю {@code important_numbers} в JSON.
     */
    @JsonProperty("important_numbers")
    List<Integer> importantNumbers;
}
