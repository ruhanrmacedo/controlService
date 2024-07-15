package macedos.controlservice.dto.comissaoTecnico;

import java.math.BigDecimal;

public record ValoresExecutadosDTO(
        BigDecimal valor1Total,
        BigDecimal valorTotal
) {}
