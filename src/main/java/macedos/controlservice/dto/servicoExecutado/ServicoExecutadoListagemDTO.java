package macedos.controlservice.dto.servicoExecutado;

import java.time.LocalDate;

public record ServicoExecutadoListagemDTO(Long id, String contrato, String os, LocalDate data, String  nomeTecnico, String descricaoServico, Double valorClaro, Double valorMacedo) {
}
