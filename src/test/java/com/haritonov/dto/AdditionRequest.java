package com.haritonov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdditionRequest {

    @JsonProperty("additional_info")
    String additionalInfo;

    @JsonProperty("additional_number")
    Integer additionalNumber;
}
