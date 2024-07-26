package macedos.controlservice.dto.usuario;

import jakarta.validation.constraints.NotNull;
import macedos.controlservice.enums.TipoUsuario;

public record EditarUsuarioDTO(
        @NotNull
        Long id,
        String nome,
        String cpf,
        String login,
        TipoUsuario tipoUsuario) {
}
