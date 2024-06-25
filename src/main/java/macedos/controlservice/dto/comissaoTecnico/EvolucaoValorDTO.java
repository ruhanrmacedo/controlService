package macedos.controlservice.dto.comissaoTecnico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

public record EvolucaoValorDTO(
        int mes,
        int ano,
        BigDecimal valor1
) {
    public LocalDate getData() {
        return YearMonth.of(ano, mes).atDay(1);
    }
}
