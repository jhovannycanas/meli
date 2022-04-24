package co.meli.challeng.mutant.resources;

import co.meli.challeng.mutant.model.dto.SequenceDto;
import co.meli.challeng.mutant.model.dto.Stat;
import co.meli.challeng.mutant.services.MutantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MutantResource implements MutantApi{

    private final MutantService mutantService;

    @Override
    public ResponseEntity<Void> mutant(SequenceDto sequenceDto) {
        if (mutantService.isMutant(sequenceDto.getDna().toArray(new String[0]))){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    public ResponseEntity<Stat> stat() {
        return ResponseEntity.ok(mutantService.getStat());
    }
}
