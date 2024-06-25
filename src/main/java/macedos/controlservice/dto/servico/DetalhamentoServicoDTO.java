package macedos.controlservice.dto.servico;

import macedos.controlservice.entity.Servico;
import macedos.controlservice.enums.TipoServico;

public record DetalhamentoServicoDTO(Long idServico,
                                     String descricao,
                                     Double valor1,
                                     Double valor2,
                                     TipoServico tipoServico) {

    public DetalhamentoServicoDTO(Servico servico) {
        this(servico.getIdServico(),
                servico.getDescricao(),
                servico.getValor1(),
                servico.getValor2(),
                servico.getTipoServico());
    }

}
