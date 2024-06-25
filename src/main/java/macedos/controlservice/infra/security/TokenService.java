package macedos.controlservice.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import macedos.controlservice.entity.Usuario;
import macedos.controlservice.infra.exception.UsuarioDesligadoException;
import macedos.controlservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${controlService.security.token.secret}")
    private String secret;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String gerarToken(String username) {
        try {
            // Verifica se o usuário está ativo antes de gerar o token
            Usuario usuario = usuarioRepository.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o login: " + username));

            if (usuario.getDataInativacao() != null && !usuario.getDataInativacao().isAfter(LocalDate.now())) {
                throw new UsuarioDesligadoException("Usuário " + username + " inativado do sistema.");
            }

            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("controlService")
                    .withSubject(username)
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("controlService")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


}
