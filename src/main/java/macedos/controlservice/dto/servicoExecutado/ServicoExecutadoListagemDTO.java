package macedos.controlservice.dto.servicoExecutado;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record ServicoExecutadoListagemDTO(
        Long id,
        String contrato,
        String os,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")LocalDate data,
        String  nomeTecnico,
        String descricaoServico,
        List<String> descricaoServicosAdicionais,
        Double valor1,
        Double valorTotal) {
}
