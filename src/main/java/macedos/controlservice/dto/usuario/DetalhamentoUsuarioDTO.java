package macedos.controlservice.dto.usuario;

import macedos.controlservice.entity.Usuario;

import java.time.LocalDate;

public record DetalhamentoUsuarioDTO(
    Long id,
    String nome,
    String cpf,
    String login,
    String senha,
    LocalDate dataAtivacao,
    LocalDate dataInativacao) {

    public DetalhamentoUsuarioDTO(Usuario usuario) {
        this (
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getDataAtivacao(),
                usuario.getDataInativacao()
        );
    }
}
