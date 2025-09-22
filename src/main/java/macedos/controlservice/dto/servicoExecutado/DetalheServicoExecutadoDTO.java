package macedos.controlservice.dto.servicoExecutado;

import java.time.LocalDate;
import java.util.List;

public record DetalheServicoExecutadoDTO(
        Long id, String contrato, String os, LocalDate data,
        Long idTecnico, String nomeTecnico,
        Long idServico, String descricaoServico,
        List<ServicoAdicionalDTO> servicosAdicionais,
        String nomeCliente, Double metragemCaboDrop
) {}
