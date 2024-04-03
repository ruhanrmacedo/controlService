package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import macedos.controlservice.dto.servicoExecutado.DetalhamentoRegistrarServDTO;
import macedos.controlservice.dto.servicoExecutado.EditarServicoExecutadoDTO;
import macedos.controlservice.dto.servicoExecutado.RegistrarServicoDTO;
import macedos.controlservice.dto.servicoExecutado.ServicoExecutadoListagemDTO;
import macedos.controlservice.entity.Servico;
import macedos.controlservice.entity.ServicoExecutado;
import macedos.controlservice.service.ServicoExecutadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listarServicosExecutados")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<?> listagemServicoExecutado(@PageableDefault(sort = "data") Pageable paginacao) {
        try {
            Page<ServicoExecutadoListagemDTO> servicosExecutadosDTO = servicoExecutadoService.listagemServicoExecutado(paginacao);
            return ResponseEntity.ok(servicosExecutadosDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a sua requisição: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluirServicoExecutado/{id}")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity excluirServicoExecutado(@PathVariable Long id) {
        servicoExecutadoService.excluirServicoExecutado(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/editarServicoExecutado")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<DetalhamentoRegistrarServDTO> editarServicoExecutado(@Valid @RequestBody EditarServicoExecutadoDTO dados) {
        ServicoExecutado servicoEditado = servicoExecutadoService.editarServicoExecutado(dados);
        DetalhamentoRegistrarServDTO detalhamentoRegistrarServDTO = new DetalhamentoRegistrarServDTO(servicoEditado);
        return ResponseEntity.ok(detalhamentoRegistrarServDTO);
    }
}
