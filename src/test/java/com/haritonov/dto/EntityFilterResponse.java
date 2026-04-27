package com.haritonov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Объект-обертка для ответа на запрос получения всех сущностей ({@code GET /api/getAll}).
 * Содержит список сущностей и информацию о пагинации.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityFilterResponse {

    /**
     * Список сущностей, полученных от сервера.
     * Соответствует полю {@code entity} в JSON.
     */
    @JsonProperty("entity")
    private List<EntityResponse> entity;

    /**
     * Номер текущей страницы пагинации.
     * Соответствует полю {@code page} в JSON.
     */
    @JsonProperty("page")
    private Integer page;

    /**
     * Количество элементов на странице.
     * Соответствует полю {@code perPage} в JSON.
     */
    @JsonProperty("perPage")
    private Integer perPage;
}
