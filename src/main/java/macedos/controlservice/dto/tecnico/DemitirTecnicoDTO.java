package macedos.controlservice.dto.tecnico;

import java.time.LocalDate;

public record DemitirTecnicoDTO(
        Long idTecnico,
        LocalDate dataDesligamento) {
}
