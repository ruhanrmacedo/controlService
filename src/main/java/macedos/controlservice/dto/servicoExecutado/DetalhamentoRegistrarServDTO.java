package macedos.controlservice.dto.servicoExecutado;

import macedos.controlservice.entity.ServicoExecutado;

import java.time.LocalDate;

public record DetalhamentoRegistrarServDTO(Long id,
                                           String contrato,
                                           String os,
                                           LocalDate data,
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
