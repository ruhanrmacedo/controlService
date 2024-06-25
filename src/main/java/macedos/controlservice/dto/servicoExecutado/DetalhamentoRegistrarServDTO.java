package macedos.controlservice.dto.servicoExecutado;

import com.fasterxml.jackson.annotation.JsonFormat;
import macedos.controlservice.entity.ServicoExecutado;

import java.time.LocalDate;

public record DetalhamentoRegistrarServDTO(
        Long id,
        String contrato,
        String os,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate data,
        Long idTecnico,
        Long idServico) {
    public DetalhamentoRegistrarServDTO(ServicoExecutado servicoEditado) {
        this(servicoEditado.getId(),
                servicoEditado.getContrato(),
                servicoEditado.getOs(),
                servicoEditado.getData(),
                servicoEditado.getTecnico().getIdTecnico(),
                servicoEditado.getServico().getIdServico());
    }
}
