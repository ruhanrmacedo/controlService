package macedos.controlservice.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record EditarUsuarioDTO(
        @NotNull
        Long id,
        String nome,
        String cpf,
        String login) {
}
