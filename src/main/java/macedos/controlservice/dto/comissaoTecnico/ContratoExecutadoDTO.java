package macedos.controlservice.dto.comissaoTecnico;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoExecutadoDTO(
        Long id,
        String contrato,
        String os,
        LocalDate data,
        String nomeTecnico,
        String descricaoServico,
        BigDecimal valorClaro,
        BigDecimal valorMacedo,
        BigDecimal comissao
) {
}
