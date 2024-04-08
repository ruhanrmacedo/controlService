package macedos.controlservice.dto.comissaoTecnico;

import java.math.BigDecimal;

public record ValoresExecutadosDTO(
        BigDecimal valorMacedoTotal,
        BigDecimal valorClaroTotal
) {}
