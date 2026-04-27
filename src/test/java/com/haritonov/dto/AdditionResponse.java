package com.haritonov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект ответа, содержащий дополнительные данные сущности.
 * Возвращается как вложенный объект внутри {@link EntityResponse}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionResponse {

    /**
     * Уникальный идентификатор дополнительных данных.
     */
    private Integer id;

    /**
     * Дополнительная текстовая информация.
     * Соответствует полю {@code additional_info} в JSON.
     */
    @JsonProperty("additional_info")
    private String additionalInfo;

    /**
     * Дополнительное числовое значение.
     * Соответствует полю {@code additional_number} в JSON.
     */
    @JsonProperty("additional_number")
    private Integer additionalNumber;
}
