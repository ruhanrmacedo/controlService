package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import macedos.controlservice.dto.servicoExecutado.DetalhamentoRegistrarServDTO;
import macedos.controlservice.dto.servicoExecutado.RegistrarServicoDTO;
import macedos.controlservice.entity.ServicoExecutado;
import macedos.controlservice.service.ServicoExecutadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/servicoExecutado")
@SecurityRequirement(name = "bearer-key")
public class ServicoExecutadoController {

    @Autowired
    ServicoExecutadoService servicoExecutadoService;

    @PostMapping("/registrarServico")
    @Transactional
    public ResponseEntity resgistrarServico(@RequestBody @Valid RegistrarServicoDTO dados) {
        ServicoExecutado servicoExecutado = new ServicoExecutado();
        servicoExecutadoService.registrarServico(dados);
        return ResponseEntity.ok(new DetalhamentoRegistrarServDTO(servicoExecutado.getId(), dados.contrato(), dados.os(), dados.data(), dados.idTecnico(), dados.idServico()));
    }
}
