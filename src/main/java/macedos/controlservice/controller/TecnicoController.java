package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import macedos.controlservice.dto.servico.ListagemTecnicoDTO;
import macedos.controlservice.dto.tecnico.CadastroTecnicoDTO;
import macedos.controlservice.dto.tecnico.DemitirTecnicoDTO;
import macedos.controlservice.dto.tecnico.DetalhamentoTecnicoDTO;
import macedos.controlservice.dto.tecnico.EditarTecnicosDTO;
import macedos.controlservice.entity.Tecnico;
import macedos.controlservice.service.TecnicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api/tecnicos")
@SecurityRequirement(name = "bearer-key")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService =  tecnicoService;
    }

    @PostMapping("/cadastrarTecnico")
    @Transactional
    public ResponseEntity<DetalhamentoTecnicoDTO> cadastrar(@Valid @RequestBody CadastroTecnicoDTO cadastroTecnicoDTO, UriComponentsBuilder uriBuilder) {
        Tecnico tecnico = new Tecnico(cadastroTecnicoDTO);
        tecnicoService.cadastrarTecnico(tecnico);
        var uri = uriBuilder.path("/api/tecnicos/cadastrarTecnico/{idServico}").buildAndExpand(tecnico.getIdTecnico()).toUri();
        return ResponseEntity.created(uri).body(new DetalhamentoTecnicoDTO(tecnico));
    }

    @GetMapping("/listarTecnicos")
    public ResponseEntity<Page<ListagemTecnicoDTO>> listar(@PageableDefault(sort = "nome") Pageable paginacao) {
        Page<Tecnico> tecnicos = tecnicoService.listarTecnicosDTO(paginacao);
        Page<ListagemTecnicoDTO> tecnicosDTO = tecnicos.map(ListagemTecnicoDTO::new);
        return ResponseEntity.ok(tecnicosDTO);
    }

    @GetMapping ("/listarTodosTecnicos")
    public ResponseEntity<Page<Tecnico>> listarTodosTecnicos(@PageableDefault(sort = "nome") Pageable paginacao) {
        Page<Tecnico> tecnicos = tecnicoService.listarTodosTecnicos(paginacao);
        return ResponseEntity.ok(tecnicos);
    }

    @PutMapping("/editarTecnico")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<DetalhamentoTecnicoDTO> editarTecnico(@Valid @RequestBody EditarTecnicosDTO dados) {
        Tecnico tecnicoEditado = tecnicoService.editarTecnicos(dados);
        DetalhamentoTecnicoDTO detalhamentoTecnicoDTO = new DetalhamentoTecnicoDTO(tecnicoEditado);
        return ResponseEntity.ok(detalhamentoTecnicoDTO);
    }

    @PutMapping("/demitirTecnico")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<DetalhamentoTecnicoDTO> demitirTecnico(@Valid @RequestBody DemitirTecnicoDTO dados) {
        Tecnico tecnicoDemitido = tecnicoService.demitirTecnico(dados);
        DetalhamentoTecnicoDTO detalhamentoTecnicoDTO = new DetalhamentoTecnicoDTO(tecnicoDemitido);
        return ResponseEntity.ok(detalhamentoTecnicoDTO);
    }

    @PutMapping("/readmitirTecnico/{idTecnico}")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<DetalhamentoTecnicoDTO> readmitirTecnico(@PathVariable Long idTecnico) {
        Tecnico tecnicoReadmitido = tecnicoService.readmitirTecnico(idTecnico);
        DetalhamentoTecnicoDTO detalhamentoTecnicoDTO = new DetalhamentoTecnicoDTO(tecnicoReadmitido);
        return ResponseEntity.ok(detalhamentoTecnicoDTO);
    }

    @DeleteMapping("/excluirTecnico/{idTecnico}")
    @Secured({"ROLE_ROOT"})
    public ResponseEntity excluirTecnicoGerente(@PathVariable Long idTecnico) {
        tecnicoService.excluirTecnicoGerente(idTecnico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalharTecnico/{idTecnico}")
    public ResponseEntity detalharTecnico(@PathVariable Long idTecnico) {
        Tecnico tecnicoDetalhar = tecnicoService.detalharTecnico(idTecnico);
        DetalhamentoTecnicoDTO detalhamentoTecnicoDTO = new DetalhamentoTecnicoDTO(tecnicoDetalhar);
        return ResponseEntity.ok(detalhamentoTecnicoDTO);
    }
}
