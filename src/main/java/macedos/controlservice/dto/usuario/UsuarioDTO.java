package macedos.controlservice.dto.usuario;

import macedos.controlservice.entity.Usuario;
import macedos.controlservice.enums.TipoUsuario;

public record UsuarioDTO (
        String nome,
        String cpf,
        String login,
        TipoUsuario tipoUsuario){

    public UsuarioDTO(Usuario usuario) {
        this(
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getLogin(),
                usuario.getTipoUsuario());
    }
}
