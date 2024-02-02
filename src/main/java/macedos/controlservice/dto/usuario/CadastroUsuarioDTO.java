package macedos.controlservice.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import macedos.controlservice.enums.TipoUsuario;

import java.time.LocalDate;

public record CadastroUsuarioDTO(
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        @NotBlank
        String login,
        @NotNull
        String senha,
        @NotNull
        TipoUsuario tipoUsuario,
        @NotNull
        LocalDate dataAtivacao,
        LocalDate dataInativacao) {
}
