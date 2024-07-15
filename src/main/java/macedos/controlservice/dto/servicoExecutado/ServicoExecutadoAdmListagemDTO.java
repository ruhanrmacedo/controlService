package macedos.controlservice.dto.servicoExecutado;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ServicoExecutadoAdmListagemDTO(
        Long id,
        String contrato,
        String os,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate data,
        String nomeTecnico,
        String descricaoServico,
        String descricaoServicosAdicionais
) {
}
