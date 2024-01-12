package macedos.controlservice.dto.usuario;

import macedos.controlservice.entity.Usuario;
import macedos.controlservice.enums.TipoUsuario;

public class UsuarioDTO {
    private String nome;
    private TipoUsuario tipoUsuario;

    public UsuarioDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.tipoUsuario = usuario.getTipoUsuario();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
