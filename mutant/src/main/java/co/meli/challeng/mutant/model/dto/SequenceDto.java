package co.meli.challeng.mutant.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
public class SequenceDto {
    List<String> dna;
}
