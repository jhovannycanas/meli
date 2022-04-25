package co.meli.challeng.mutant.resources;

import co.meli.challeng.mutant.model.dto.SequenceDto;
import co.meli.challeng.mutant.model.dto.Stat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@RequestMapping(value = "/api/v1/mutants")
@Api(value = "Mutant System")
public interface MutantApi {

    @ApiOperation(value = "Validate a DNA sequence", nickname = "mutant",
            notes = "<h3>Validates the DNA sequence of a mutant.</h3>", tags={ "Mutant", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 403, message = "request has been refused"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/mutant",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> mutant(@Valid @RequestBody SequenceDto sequenceDto);

    @ApiOperation(value = "Get statistics", nickname = "stats",
            notes = "<h3>Get statistics of validation of sequences.</h3>", tags={ "Mutant", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "timestamping service created")})
    @RequestMapping(value = "/stats",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Stat> stat();
}
