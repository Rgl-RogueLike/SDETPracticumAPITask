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

    private Integer id;

    @JsonProperty("additional_info")
    private String additionalInfo;

    @JsonProperty("additional_number")
    private String additionalNumber;
}
