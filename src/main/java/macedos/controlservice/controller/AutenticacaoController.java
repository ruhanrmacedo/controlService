package macedos.controlservice.controller;

import jakarta.validation.Valid;
import macedos.controlservice.dto.autenticacao.AutenticacaoDTO;
import macedos.controlservice.infra.security.DadosTokenJWT;
import macedos.controlservice.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/efetuarLogin")
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(autenticacaoDTO.login(), autenticacaoDTO.senha());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Gera o token com base no nome de usuário (login)
            String tokenJWT = tokenService.gerarToken(userDetails.getUsername());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro na autenticação: " + e.getMessage());
        }
    }
}
