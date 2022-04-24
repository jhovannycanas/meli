package co.meli.challeng.mutant.services;

import co.meli.challeng.mutant.model.dto.Stat;

public interface MutantService {

    boolean isMutant(String[] dna);

    Stat getStat();
}
