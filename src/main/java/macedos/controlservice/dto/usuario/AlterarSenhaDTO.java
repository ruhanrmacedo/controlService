package macedos.controlservice.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record AlterarSenhaDTO(
        @NotBlank
        String novaSenha,
        @NotBlank
        String confirmarSenha) {
}
