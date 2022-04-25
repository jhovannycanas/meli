package co.meli.challeng.mutant.services.impl;

import co.meli.challeng.mutant.exceptions.MutantException;
import co.meli.challeng.mutant.model.dto.Stat;
import co.meli.challeng.mutant.model.entities.SequenceDna;
import co.meli.challeng.mutant.repositories.SequenceDnaRepository;
import co.meli.challeng.mutant.services.MutantService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantServiceImpl implements MutantService {

    private final SequenceDnaRepository sequenceDnaRepository;
    final int LENGTH_DNA_MUTANT = 4;
    final int MAX_LENGTH_SEARCH_DNA = 3;

    @Override
    @CacheEvict(cacheNames = "stats", allEntries = true)
    @Cacheable(cacheNames = "mutants")
    public boolean isMutant(String[] dna) {

        final int rows = dna.length;
        final int columns = dna[0].toCharArray().length;
        if (rows < LENGTH_DNA_MUTANT || columns < MAX_LENGTH_SEARCH_DNA) {
            throw new MutantException("the nitrogenous base of the DNA is not of the proper length");
        }
        this.validateStructureSequence(dna);
        boolean mutant = validateSequenceDna(dna, rows, columns);
        Optional<SequenceDna> sequenceDna = sequenceDnaRepository.findByDna(Arrays.toString(dna));
        if (sequenceDna.isPresent()) {
            return sequenceDna.get().getIsMutant();
        } else {
            sequenceDnaRepository.save(SequenceDna.builder().dna(Arrays.toString(dna)).isMutant(mutant).build());
        }
        return mutant;
    }

    @Override
    @Cacheable(cacheNames = "stats")
    public Stat getStat() {
        Long totalMutant = sequenceDnaRepository.countAllByIsMutantIsTrue();
        Long totalHuman = sequenceDnaRepository.countAllByIsMutantIsFalse();
        Long totalSequenceDna = sequenceDnaRepository.count();
        return Stat.builder().countDnaMutant(totalMutant).countDnaHuman(totalHuman)
                .ratio(totalMutant.doubleValue() / totalSequenceDna.doubleValue()).build();
    }

    private boolean validateSequenceDna(String[] dna, int rows, int columns) {
        int acumulateMatchDnaMutant = 0;
        acumulateMatchDnaMutant += this.validateSequenceDnaRows(dna, rows, columns);
        acumulateMatchDnaMutant += this.validateSequenceDnaColumns(dna, rows, columns);
        acumulateMatchDnaMutant += this.validateSequenceDiagonalLeftToRight(dna, rows, columns);
        acumulateMatchDnaMutant += this.validateSequenceDiagonalRigthToLeft(dna, rows, columns);
        acumulateMatchDnaMutant += this.validateSequenceDiagonalRightToUp(dna, rows, columns);
        acumulateMatchDnaMutant += this.validateSequenceDiagonalLeftToUp(dna, rows, columns);
        return acumulateMatchDnaMutant >= 2;
    }

    private int validateSequenceDiagonalLeftToRight(String[] dna, int rows, int columns) {
        int acumulateDiagonalLeftToRight = 0;
        for (int row = 0; row < rows - MAX_LENGTH_SEARCH_DNA; row++) {
            for (int col = columns - 1; col >= columns - MAX_LENGTH_SEARCH_DNA; col--) {
                int p = row;
                int c = col;
                int counter = 1;
                while (dna[p].charAt(c) == dna[p + 1].charAt(c - 1) && p <= columns - MAX_LENGTH_SEARCH_DNA) {
                    counter++;
                    if (counter == LENGTH_DNA_MUTANT) {
                        acumulateDiagonalLeftToRight++;
                    }
                    p = p + 1;
                    c = c - 1;
                }
            }
        }
        return acumulateDiagonalLeftToRight;
    }

    private int validateSequenceDnaRows(String[] dna, int rows, int columns) {
        int acumulateDnaRows = 0;
        for (int row = 0; row < rows; row++) {
            int left = 0;
            int r = 1;
            int counter = 1;
            while (left <= columns - MAX_LENGTH_SEARCH_DNA) {
                if (dna[row].charAt(left) == dna[row].charAt(r)) {
                    r++;
                    if (r == columns) {
                        left = r;
                    }
                    counter++;
                    if (counter == LENGTH_DNA_MUTANT) {
                        acumulateDnaRows++;
                    }
                } else {
                    counter = 1;
                    left = r;
                    r++;
                }
            }
        }
        return acumulateDnaRows;
    }

    private int validateSequenceDnaColumns(String[] dna, int rows, int columns) {
        int acumulateDnaColumns = 0;
        for (int column = 0; column < columns; column++) {
            int up = 0;
            int down = 1;
            int counter = 1;
            while (up <= columns - MAX_LENGTH_SEARCH_DNA) {
                if (dna[up].charAt(column) == dna[down].charAt(column)) {
                    down++;
                    if (down == columns) {
                        up = down;
                    }
                    counter++;
                    if (counter == LENGTH_DNA_MUTANT) {
                        acumulateDnaColumns++;
                    }
                } else {
                    counter = 1;
                    up = down;
                    down++;
                }
            }
        }
        return acumulateDnaColumns;
    }

    private int validateSequenceDiagonalRigthToLeft(String[] dna, int rows, int columns) {
        int acumulateDiagonalRigthToLeft = 0;
        for (int row = 0; row < rows - MAX_LENGTH_SEARCH_DNA; row++) {
            for (int col = 0; col < columns - MAX_LENGTH_SEARCH_DNA; col++) {
                int p = row;
                int c = col;
                int counter = 1;
                while (dna[p].charAt(c) == dna[p + 1].charAt(c + 1) && p < columns - MAX_LENGTH_SEARCH_DNA) {
                    counter++;
                    if (counter == LENGTH_DNA_MUTANT) {
                        acumulateDiagonalRigthToLeft++;
                    }
                    p = p + 1;
                    c = c + 1;
                }
            }
        }
        return acumulateDiagonalRigthToLeft;
    }

    private int validateSequenceDiagonalRightToUp(String[] dna, int rows, int columns) {
        int acumulateDiagonalRightToUp = 0;
        for (int row = rows - 1; row > rows - MAX_LENGTH_SEARCH_DNA; row--) {
            for (int col = columns - 1; col > columns - MAX_LENGTH_SEARCH_DNA; col--) {
                int p = row;
                int c = col;
                int counter = 1;

                while (dna[p].charAt(c) == dna[p - 1].charAt(c - 1) && p > columns - MAX_LENGTH_SEARCH_DNA) {
                    counter++;
                    if (counter == LENGTH_DNA_MUTANT) {
                        acumulateDiagonalRightToUp++;
                    }
                    p = p - 1;
                    c = c - 1;
                }
            }
        }
        return acumulateDiagonalRightToUp;
    }

    private int validateSequenceDiagonalLeftToUp(String[] dna, int rows, int columns) {
        int acumulateDiagonalLeftToUp = 0;
        for (int row = rows - 1; row >= rows - MAX_LENGTH_SEARCH_DNA; row--) {
            for (int col = 0; col < columns - MAX_LENGTH_SEARCH_DNA; col++) {
                int p = row;
                int c = col;
                int counter = 1;
                while (dna[p].charAt(c) == dna[p - 1].charAt(c + 1) && c < columns - MAX_LENGTH_SEARCH_DNA) {
                    counter++;
                    if (counter == LENGTH_DNA_MUTANT) {
                        acumulateDiagonalLeftToUp++;
                    }
                    p = p - 1;
                    c = c + 1;
                }
            }
        }
        return acumulateDiagonalLeftToUp;
    }

    private void validateStructureSequence(String[] dna) {
        final String REGEX = "[ACGT]+";
        for (String sequence:dna) {
            if (!sequence.toUpperCase().matches(REGEX)) {
                throw new MutantException("the nitrogenous base of DNA does not have the expected structure: (A,T,C,G)");
            }
        }
    }
}
