package macedos.controlservice.dto;

import java.time.LocalDate;

public record DemitirTecnicoDTO(
        Long idTecnico,
        LocalDate dataDesligamento) {
}
