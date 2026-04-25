package com.haritonov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionResponse {

    Integer id;

    @JsonProperty("additional_info")
    String additionalInfo;

    @JsonProperty("additional_number")
    String additionalNumber;
}
