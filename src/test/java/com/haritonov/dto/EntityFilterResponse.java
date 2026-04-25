package com.haritonov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityFilterResponse {

    @JsonProperty("entity")
    private List<EntityResponse> entity;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("perPage")
    private Integer perPage;
}
