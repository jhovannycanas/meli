package co.meli.challeng.mutant.services.impl;

import co.meli.challeng.mutant.services.MutantService;
import org.springframework.stereotype.Service;

@Service
public class MutantServiceImpl implements MutantService {

    @Override
    public boolean isMutant(String[] dna) {
        return false;
    }
}
