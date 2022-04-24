package co.meli.challeng.mutant.exceptions;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@ResponseStatus(HttpStatus.FORBIDDEN)
public class MutantException extends RuntimeException{

    public MutantException(String message) {
        super(message);
    }
}
