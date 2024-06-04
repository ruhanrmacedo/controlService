package macedos.controlservice.dto.servico;

import com.fasterxml.jackson.annotation.JsonFormat;
import macedos.controlservice.entity.Tecnico;

import java.time.LocalDate;

public record ListagemTecnicoDTO(
        Long idTecnico,
        String nome,
        String login,
        String placa,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate dataAdmissao) {

    public ListagemTecnicoDTO(Tecnico tecnico) {
        this(tecnico.getIdTecnico(),
                tecnico.getNome(),
                tecnico.getLogin(),
                tecnico.getPlaca(),
                tecnico.getDataAdmissao());
    }

}
