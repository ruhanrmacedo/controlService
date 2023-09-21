package macedos.controlservice.dto.servico;

import macedos.controlservice.entity.Tecnico;

import java.time.LocalDate;

public record ListagemTecnicoDTO(
        Long idTecnico,
        String nome,
        String login,
        String placa,
        LocalDate dataAdmissao) {

    public ListagemTecnicoDTO(Tecnico tecnico) {
        this(tecnico.getIdTecnico(),
                tecnico.getNome(),
                tecnico.getLogin(),
                tecnico.getPlaca(),
                tecnico.getDataAdmissao());
    }

}
