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
        BigDecimal valor1,
        BigDecimal valor2,
        BigDecimal comissao
) {
}
