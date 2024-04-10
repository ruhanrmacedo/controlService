package macedos.controlservice.dto.servicoExecutado;

import java.time.LocalDate;

public record ListagemServExecutadosAdmDTO(
        Long id,
        String contrato,
        String os,
        LocalDate data,
        String nomeTecnico,
        String descricaoServico
) {
}
