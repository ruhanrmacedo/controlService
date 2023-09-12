package macedos.controlservice.tecnico;

import java.time.LocalDate;

public record DadosListagemTecnico(
        Long idTecnico,
        String nome,
        String login,
        String placa,
        LocalDate dataAdmissao) {

    public DadosListagemTecnico(Tecnico tecnico) {
        this(tecnico.getIdTecnico(),
                tecnico.getNome(),
                tecnico.getLogin(),
                tecnico.getPlaca(),
                tecnico.getDataAdmissao());
    }

}
