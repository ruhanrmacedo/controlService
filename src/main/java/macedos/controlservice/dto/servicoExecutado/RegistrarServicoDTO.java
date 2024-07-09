package macedos.controlservice.dto.servicoExecutado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record RegistrarServicoDTO(
        @NotBlank
        String contrato,
        @NotBlank
        String os,
        @NotNull
        LocalDate data,
        @NotNull
        Long idTecnico,
        @NotNull
        Long idServico,
        List<Long> servicosAdicionais,
        Double bonificacao,
        Double percentualAcaoFinalDeSemana) {
}
