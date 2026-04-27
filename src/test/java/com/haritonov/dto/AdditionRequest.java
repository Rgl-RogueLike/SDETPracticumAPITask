package com.haritonov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

/**
 * Объект запроса для передачи дополнительных данных.
 * Используется внутри {@link EntityRequest} при создании или обновлении сущности.
 */
@Value
@Builder
public class AdditionRequest {

    /**
     * Дополнительная текстовая информация о сущности.
     * Соответствует полю {@code additional_info} в JSON.
     */
    @JsonProperty("additional_info")
    String additionalInfo;

    /**
     * Дополнительное числовое значение.
     * Соответствует полю {@code additional_number} в JSON.
     */
    @JsonProperty("additional_number")
    Integer additionalNumber;
}
