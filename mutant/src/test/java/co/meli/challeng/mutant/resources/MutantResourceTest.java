package co.meli.challeng.mutant.resources;

import co.meli.challeng.mutant.model.dto.SequenceDto;
import co.meli.challeng.mutant.model.entities.SequenceDna;
import co.meli.challeng.mutant.repositories.SequenceDnaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
class MutantResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SequenceDnaRepository sequenceDnaRepository;

    @BeforeAll
    void setup() {
        SequenceDna sequenceDna = SequenceDna.builder().dna("\"ATGCGA\",\"CAGTAC\",\"GGGGGT\",\"AGTAGG\",\"CCCCTA\",\"TCACTC\"")
                .isMutant(Boolean.TRUE).build();
        sequenceDnaRepository.save(sequenceDna);
    }

    final String[] dnaMutant = {"ATGCGA","CAGTAC","TTTTGT","AGTAGG","CCCCTA","TCACTC"};
    final String[] dnaHuman = {"ATGCGA","CAGTAC","TTATGT","AGTAGG","CCCTTA","TCACTC"};
    final String[] dnaLength = {"ATGCGA","CAGTAC","TTATGT"};
    final String[] dnaStructure = {"ATGCGA","CAGTAC","TTATGT","AGTAGG","CCCTTA","TCACTH"};

    @Test
    void mutant() throws Exception {
        SequenceDto sequenceDto = new SequenceDto();
        sequenceDto.setDna(Arrays.asList(dnaMutant));
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mutants/mutants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sequenceDto))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void mutantHuman() throws Exception {
        SequenceDto sequenceDto = new SequenceDto();
        sequenceDto.setDna(Arrays.asList(dnaHuman));
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mutants/mutants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sequenceDto))
        ).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void mutantLengthException() throws Exception {
        SequenceDto sequenceDto = new SequenceDto();
        sequenceDto.setDna(Arrays.asList(dnaLength));
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mutants/mutants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sequenceDto))
        ).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void mutantStructureException() throws Exception {
        SequenceDto sequenceDto = new SequenceDto();
        sequenceDto.setDna(Arrays.asList(dnaStructure));
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mutants/mutants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sequenceDto))
        ).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void stat() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/mutants/stats"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}