package macedos.controlservice.dto.servicoExecutado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

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
        Long idServico) {
}
