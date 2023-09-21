package macedos.controlservice.dto.servico;

import macedos.controlservice.entity.Servico;
import macedos.controlservice.enums.TipoServico;

public record DetalhamentoServicoDTO(Long idServico,
                                     String descricao,
                                     Double valorClaro,
                                     Double valorMacedo,
                                     TipoServico tipoServico) {

    public DetalhamentoServicoDTO(Servico servico) {
        this(servico.getIdServico(),
                servico.getDescricao(),
                servico.getValorClaro(),
                servico.getValorMacedo(),
                servico.getTipoServico());
    }

}
