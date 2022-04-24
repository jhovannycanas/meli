package co.meli.challeng.mutant.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "sequence_dna")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SequenceDna {

    @Id
    @GeneratedValue()
    private UUID uuid;

    @Column(unique = true, nullable = false)
    private String dna;

    @Column(nullable = false)
    private Boolean isMutant;
}
