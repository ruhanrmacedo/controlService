package macedos.controlservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import macedos.controlservice.dto.usuario.CadastroUsuarioDTO;
import macedos.controlservice.enums.TipoUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;
    private TipoUsuario tipoUsuario;

    public Usuario(CadastroUsuarioDTO cadastroUsuarioDTO) {
        this.nome = cadastroUsuarioDTO.nome();
        this.cpf = cadastroUsuarioDTO.cpf();
        this.login = cadastroUsuarioDTO.login();
        this.senha = cadastroUsuarioDTO.senha();
        this.tipoUsuario = cadastroUsuarioDTO.tipoUsuario();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Aqui, você pode mapear o tipo de usuário para autorizações específicas
        switch (tipoUsuario) {
            case ADMINISTRADOR:
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            case GERENTE:
                authorities.add(new SimpleGrantedAuthority("ROLE_GERENTE"));
                break;
            case ROOT:
                authorities.add(new SimpleGrantedAuthority("ROLE_ROOT"));
                break;
            default:
                authorities.add(new SimpleGrantedAuthority("ROLE_USUARIO"));
                break;
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
