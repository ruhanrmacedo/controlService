package macedos.controlservice.dto;

import macedos.controlservice.entity.Servico;
import macedos.controlservice.entity.TipoServico;

public record ListagemServicosDTO(
    Long idServico,
    String descricao,
    TipoServico tipoServico){

    public ListagemServicosDTO(Servico servico) {
        this(servico.getIdServico(), servico.getDescricao(), servico.getTipoServico());
    }
}
