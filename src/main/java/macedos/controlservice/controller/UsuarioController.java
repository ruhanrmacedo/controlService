package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import macedos.controlservice.dto.usuario.CadastroUsuarioDTO;
import macedos.controlservice.dto.usuario.UsuarioDTO;
import macedos.controlservice.dto.usuario.UsuarioDetalhesDTO;
import macedos.controlservice.entity.Usuario;
import macedos.controlservice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrarUsuario")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<UsuarioDetalhesDTO> cadastrarUsuario(@Valid @RequestBody CadastroUsuarioDTO cadastroUsuarioDTO, UriComponentsBuilder uriBuilder){
        Usuario usuario = new Usuario();
        usuario.setNome(cadastroUsuarioDTO.nome());
        usuario.setCpf(cadastroUsuarioDTO.cpf());
        usuario.setLogin(cadastroUsuarioDTO.login());
        usuario.setSenha(cadastroUsuarioDTO.senha());
        usuario.setTipoUsuario(cadastroUsuarioDTO.tipoUsuario());

        Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuario);
        var uri = uriBuilder.path("api/usuarios/cadastrarUsuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDetalhesDTO(usuarioSalvo));
    }

    @GetMapping("/usuarioAtual")
    public ResponseEntity<UsuarioDTO> buscarUsuarioAtual() {
        UsuarioDTO usuarioDTO = usuarioService.buscarUsuarioAtual();
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/usuarioAtualDetalhamento")
    public ResponseEntity<UsuarioDetalhesDTO> buscarUsuarioAtualDetalhado() {
        UsuarioDetalhesDTO usuarioDetalhesDTO = usuarioService.buscarInformacoesDetalhadasUsuario();
        return ResponseEntity.ok(usuarioDetalhesDTO);
    }


}
