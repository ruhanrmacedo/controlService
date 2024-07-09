package macedos.controlservice.dto.servicoExecutado;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record EditarServicoExecutadoDTO(
        @NotNull
        Long id,
        String contrato,
        String os,
        LocalDate data,
        Long idTecnico,
        Long idServico,
        List<Long> servicosAdicionais,
        Double bonificacao,
        Double percentualAcaoFinalDeSemana
) {

}
