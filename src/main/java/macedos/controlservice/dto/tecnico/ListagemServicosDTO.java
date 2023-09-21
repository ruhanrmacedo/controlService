package macedos.controlservice.dto.tecnico;

import macedos.controlservice.entity.Servico;
import macedos.controlservice.enums.TipoServico;

public record ListagemServicosDTO(
    Long idServico,
    String descricao,
    TipoServico tipoServico){

    public ListagemServicosDTO(Servico servico) {
        this(servico.getIdServico(), servico.getDescricao(), servico.getTipoServico());
    }
}
