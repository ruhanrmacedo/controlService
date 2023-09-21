package macedos.controlservice.dto.servico;

import jakarta.validation.constraints.NotNull;
import macedos.controlservice.enums.TipoServico;

public record EditarServicosDTO(
        @NotNull
        Long idServico,
        String descricao,
        Double valorClaro,
        Double valorMacedo,
        TipoServico tipoServico){
}
