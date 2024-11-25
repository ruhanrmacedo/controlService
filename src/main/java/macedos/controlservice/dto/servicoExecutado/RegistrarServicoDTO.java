package macedos.controlservice.dto.servicoExecutado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
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
        String nomeCliente,
        Double metragemCaboDrop
) {
        public RegistrarServicoDTO {
                if (servicosAdicionais == null) {
                        servicosAdicionais = List.of(); // inicializa como lista vazia
                }
        }
}
