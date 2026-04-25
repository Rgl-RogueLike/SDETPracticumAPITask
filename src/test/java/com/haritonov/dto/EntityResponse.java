package com.haritonov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class EntityResponse {

    Integer id;

    String title;

    Boolean verified;

    AdditionResponse addition;

    @JsonProperty("important_numbers")
    List<Integer> importantNumbers;
}
