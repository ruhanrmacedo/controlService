package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import macedos.controlservice.dto.usuario.*;
import macedos.controlservice.entity.Usuario;
import macedos.controlservice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/listarTodosUsuarios")
    public ResponseEntity<Page<Usuario>> listarTodosUsuarios(@PageableDefault(sort = "nome")Pageable paginacao) {
        Page<Usuario> usuarios = usuarioService.listarTodosUsuarios(paginacao);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/editarUsuario")
    @Transactional
    public ResponseEntity<DetalhamentoUsuarioDTO> editarUsuario(@Valid @RequestBody EditarUsuarioDTO dados) {
        Usuario usuarioEditado = usuarioService.editarUsuario(dados);
        DetalhamentoUsuarioDTO detalhamentoUsuarioDTO = new DetalhamentoUsuarioDTO(usuarioEditado);
        return ResponseEntity.ok(detalhamentoUsuarioDTO);
    }

    @PutMapping("/alterarSenha")
    @Transactional
    public ResponseEntity<Void> alterarSenha(@RequestParam String novaSenha, @RequestParam String confirmarSenha) {
        try {
            usuarioService.alterarSenha(novaSenha, confirmarSenha);
            return ResponseEntity.ok().build();
        } catch ( IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/desligarUsuario")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<DetalhamentoUsuarioDTO> desligarUsuario(@Valid @RequestBody DesligarUsuarioDTO dados) {
        Usuario usuarioDesligado = usuarioService.desligarUsuario(dados);
        DetalhamentoUsuarioDTO detalhamentoUsuarioDTO = new DetalhamentoUsuarioDTO(usuarioDesligado);
        return ResponseEntity.ok(detalhamentoUsuarioDTO);
    }
}
