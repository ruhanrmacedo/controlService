package macedos.controlservice.dto.comissaoTecnico;

import java.math.BigDecimal;

public record ValoresExecutadosDTO(
        BigDecimal valor2Total,
        BigDecimal valor1Total
) {}
