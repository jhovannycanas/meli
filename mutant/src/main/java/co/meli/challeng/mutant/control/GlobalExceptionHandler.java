package co.meli.challeng.mutant.control;

import co.meli.challeng.mutant.exceptions.MutantException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MutantException.class)
    public ResponseEntity<String> handleEntityNotFound(MutantException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.FORBIDDEN);
    }
}
