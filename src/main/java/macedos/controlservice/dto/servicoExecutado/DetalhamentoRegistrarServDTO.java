package macedos.controlservice.dto.servicoExecutado;

import java.time.LocalDate;

public record DetalhamentoRegistrarServDTO(Long id, String contrato, String os, LocalDate data, Long idTecnico, Long idServico) {
}
