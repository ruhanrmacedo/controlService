package macedos.controlservice.dto.comissaoTecnico;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EvolucaoValorDTO(
        LocalDate data,
        BigDecimal valor
) {}
