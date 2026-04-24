package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class EntityRequest {

    String title;

    boolean verified;

    AdditionRequest addition;

    @JsonProperty("important_numbers")
    List<Integer> importantNumbers;
}
