package macedos.controlservice.tecnico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroTecnico(
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        String login,
        String placa,
        @NotNull
        LocalDate dataAdmissao,
        LocalDate dataDesligamento) {
}
