package co.meli.challeng.mutant.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@NoArgsConstructor
public class SequenceDto {
    List<String> dna;
}
