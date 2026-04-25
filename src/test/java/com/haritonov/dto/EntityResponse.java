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
public class EntityResponse {

    Integer id;

    String title;

    Boolean verified;

    AdditionResponse addition;

    @JsonProperty("important_numbers")
    List<Integer> importantNumbers;
}
