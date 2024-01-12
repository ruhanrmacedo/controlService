package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import macedos.controlservice.dto.usuario.UsuarioDTO;
import macedos.controlservice.entity.Usuario;
import macedos.controlservice.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /*@GetMapping("/nomeUsuario/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Long id) {
        UsuarioDTO usuarioDTO = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuarioDTO);
    }*/

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> buscarUsuarioAtual() {
        UsuarioDTO usuarioDTO = usuarioService.buscarUsuarioAtual();
        return ResponseEntity.ok(usuarioDTO);
    }
}
