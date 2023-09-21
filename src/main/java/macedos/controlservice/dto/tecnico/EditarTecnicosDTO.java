package macedos.controlservice.dto.tecnico;

import jakarta.validation.constraints.NotNull;

public record EditarTecnicosDTO(
        @NotNull
        Long idTecnico,
        String nome,
        String login,
        String placa) {

}
