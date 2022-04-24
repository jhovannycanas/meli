package co.meli.challeng.mutant.model.dto;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Stat {

    Long countDnaHuman;
    Long countDnaMutant;
    Double ratio;
}
