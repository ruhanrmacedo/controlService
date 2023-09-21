package macedos.controlservice.dto.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import macedos.controlservice.enums.TipoServico;

public record CadastroServicoDTO(
        @NotBlank
        String descricao,
        @NotNull
        double valorClaro,
        @NotNull
        double valorMacedo,
        @NotNull
        TipoServico tipoServico) {
}
