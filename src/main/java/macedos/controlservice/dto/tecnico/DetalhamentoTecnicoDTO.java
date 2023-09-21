package macedos.controlservice.dto.tecnico;

import macedos.controlservice.entity.Tecnico;

import java.time.LocalDate;

public record DetalhamentoTecnicoDTO(Long idTecnico,
                                     String nome,
                                     String cpf,
                                     String login,
                                     String placa,
                                     LocalDate dataAdmissao,
                                     LocalDate dataDesligamento) {

    public DetalhamentoTecnicoDTO(Tecnico tecnico){
        this(tecnico.getIdTecnico(),
                tecnico.getNome(),
                tecnico.getCpf(),
                tecnico.getLogin(),
                tecnico.getPlaca(),
                tecnico.getDataAdmissao(),
                tecnico.getDataDesligamento());
    }

}
