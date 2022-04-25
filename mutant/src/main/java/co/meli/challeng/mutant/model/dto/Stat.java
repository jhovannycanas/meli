package co.meli.challeng.mutant.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Stat {

    @JsonProperty("count_human_dna")
    Long countDnaHuman;
    @JsonProperty("count_mutant_dna")
    Long countDnaMutant;
    Double ratio;
}
