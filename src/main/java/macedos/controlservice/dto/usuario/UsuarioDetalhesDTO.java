package macedos.controlservice.dto.usuario;

import macedos.controlservice.entity.Usuario;
import macedos.controlservice.enums.TipoUsuario;

public record UsuarioDetalhesDTO (
        Long id,
        String nome,
        String cpf,
        String login,
        TipoUsuario tipoUsuario) {

    public UsuarioDetalhesDTO(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getLogin(),
                usuario.getTipoUsuario());
    }
}
