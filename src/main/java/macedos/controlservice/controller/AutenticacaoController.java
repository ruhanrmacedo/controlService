package macedos.controlservice.controller;

import jakarta.validation.Valid;
import macedos.controlservice.dto.autenticacao.AutenticacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/efetuarLogin")
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
        var token = new UsernamePasswordAuthenticationToken(autenticacaoDTO.login(), autenticacaoDTO.login());
        var authentication = authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
