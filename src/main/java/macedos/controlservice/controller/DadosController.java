package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import macedos.controlservice.dto.servicoExecutado.ServicoExecutadoListagemDTO;
import macedos.controlservice.service.DadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dados")
@SecurityRequirement(name = "bearer-key")
public class DadosController {

    @Autowired
    private DadosService dadosService;

    @GetMapping("/servicos-executados")
    public ResponseEntity<List<ServicoExecutadoListagemDTO>> getServicosExecutados() {
        List<ServicoExecutadoListagemDTO> servicosExecutados = dadosService.getAllServicosExecutados();
        return ResponseEntity.ok(servicosExecutados);
    }

}
