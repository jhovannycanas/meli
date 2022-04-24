package co.meli.challeng.mutant.repositories;

import co.meli.challeng.mutant.model.entities.SequenceDna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SequenceDnaRepository extends JpaRepository<SequenceDna, UUID> {

    Optional<SequenceDna> findByDna(String sequenceDna);

    Long countAllByIsMutantIsTrue();

    Long countAllByIsMutantIsFalse();

}
