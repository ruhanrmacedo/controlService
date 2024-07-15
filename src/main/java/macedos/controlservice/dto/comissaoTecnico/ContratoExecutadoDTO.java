package macedos.controlservice.dto.comissaoTecnico;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoExecutadoDTO(
        Long id,
        String contrato,
        String os,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")LocalDate data,
        String nomeTecnico,
        String descricaoServico,
        String descricaoServicoAdicional,
        BigDecimal valor1,
        BigDecimal valorTotal,
        BigDecimal comissao
) {
}
