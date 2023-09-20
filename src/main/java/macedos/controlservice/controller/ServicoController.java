package macedos.controlservice.controller;

import jakarta.validation.Valid;
import macedos.controlservice.dto.CadastroServicoDTO;
import macedos.controlservice.dto.DetalhamentoServicoDTO;
import macedos.controlservice.dto.EditarServicosDTO;
import macedos.controlservice.dto.ListagemServicosDTO;
import macedos.controlservice.entity.Servico;
import macedos.controlservice.service.ServicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping("/cadastrarServico")
    @Transactional
    public ResponseEntity<DetalhamentoServicoDTO> cadastrar(@Valid @RequestBody CadastroServicoDTO cadastroServicoDTO, UriComponentsBuilder uriBuilder){
            Servico servico = new Servico(cadastroServicoDTO);
            servicoService.cadastrarServico(servico);
            var uri = uriBuilder.path("api/servicos/cadastrarServico/{idServico}").buildAndExpand(servico.getIdServico()).toUri();
            return ResponseEntity.created(uri).body(new DetalhamentoServicoDTO(servico));
    }

    @GetMapping("/listarServicos")
    public ResponseEntity<Page<ListagemServicosDTO>> listar(Pageable paginacao) {
        Page<Servico> servicosAtivos = servicoService.listarServicosAtivos(paginacao);
        Page<ListagemServicosDTO> servicosDTO = servicosAtivos.map(ListagemServicosDTO::new);

        return ResponseEntity.ok(servicosDTO);
    }

    @GetMapping("/listarServicosGerente")
    public ResponseEntity<List<Servico>> listagemServicosGerente() {
        List<Servico> servicos = servicoService.listagemServicosGerente();
        return ResponseEntity.ok(servicos);
    }

    @PutMapping("/editarServico")
    @Transactional
    public ResponseEntity<DetalhamentoServicoDTO> editarServico(@Valid @RequestBody EditarServicosDTO dados) {
        Servico servicoEditado = servicoService.editarServicos(dados);
        DetalhamentoServicoDTO detalhamentoServicoDTO = new DetalhamentoServicoDTO(servicoEditado);
        return ResponseEntity.ok(detalhamentoServicoDTO);
    }

    @DeleteMapping("/exluirServico/{idServico}")
    @Transactional
    public ResponseEntity excluirServico (@PathVariable Long idServico) {
        Servico servicoExcluir = servicoService.excluirServico(idServico);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/excluirServicoGerente/{idServico}")
    public ResponseEntity excluirServicoGerente(@PathVariable Long idServico) {
        servicoService.excluirServicoGerente(idServico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalharServico/{idServico}")
    public ResponseEntity detalharServico (@PathVariable Long idServico) {
        Servico servicoDetalhar = servicoService.detalharServico(idServico);
        DetalhamentoServicoDTO detalhamentoServicoDTO = new DetalhamentoServicoDTO(servicoDetalhar);
        return ResponseEntity.ok(detalhamentoServicoDTO);
    }
}
