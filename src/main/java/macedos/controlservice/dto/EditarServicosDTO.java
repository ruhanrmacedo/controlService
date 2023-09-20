package macedos.controlservice.dto;

import jakarta.validation.constraints.NotNull;
import macedos.controlservice.entity.TipoServico;

public record EditarServicosDTO(
        @NotNull
        Long idServico,
        String descricao,
        Double valorClaro,
        Double valorMacedo,
        TipoServico tipoServico){
}
