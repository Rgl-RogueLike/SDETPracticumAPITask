package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AdditionResponse {

    Integer id;

    @JsonProperty("additional_info")
    String additionalInfo;

    @JsonProperty("additional_number")
    String additionalNumber;
}
