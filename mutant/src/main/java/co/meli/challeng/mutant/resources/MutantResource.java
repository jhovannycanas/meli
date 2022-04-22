package co.meli.challeng.mutant.resources;

import co.meli.challeng.mutant.model.dto.SequenceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/mutants")
public class MutantResource implements MutantApi{

    @Override
    public ResponseEntity<Void> mutant(SequenceDto sequenceDto) {
        return null;
    }
}
