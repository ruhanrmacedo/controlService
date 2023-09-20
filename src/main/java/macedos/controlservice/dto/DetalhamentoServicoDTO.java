package macedos.controlservice.dto;

import macedos.controlservice.entity.Servico;
import macedos.controlservice.entity.TipoServico;

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
