package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import macedos.controlservice.dto.servico.CadastroServicoDTO;
import macedos.controlservice.dto.servico.DetalhamentoServicoDTO;
import macedos.controlservice.dto.servico.EditarServicosDTO;
import macedos.controlservice.dto.tecnico.ListagemServicosDTO;
import macedos.controlservice.entity.Servico;
import macedos.controlservice.enums.TipoServico;
import macedos.controlservice.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/servicos")
@SecurityRequirement(name = "bearer-key")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping("/cadastrarServico")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<DetalhamentoServicoDTO> cadastrar(@Valid @RequestBody CadastroServicoDTO cadastroServicoDTO, UriComponentsBuilder uriBuilder){
        Servico servico = new Servico(cadastroServicoDTO);
            servicoService.cadastrarServico(servico);
            var uri = uriBuilder.path("api/servicos/cadastrarServico/{idServico}").buildAndExpand(servico.getIdServico()).toUri();
            return ResponseEntity.created(uri).body(new DetalhamentoServicoDTO(servico));
    }

    @GetMapping("/listarServicos")
    // Todos os usuários tipo administrador tem acesso a esta lista.
    public ResponseEntity<Page<ListagemServicosDTO>> listar(@PageableDefault (sort = "descricao") Pageable paginacao) {
        Page<Servico> servicosAtivos = servicoService.listarServicosAtivos(paginacao);
        Page<ListagemServicosDTO> servicosDTO = servicosAtivos.map(ListagemServicosDTO::new);

        return ResponseEntity.ok(servicosDTO);
    }

    @GetMapping("/listarServicosAtivos")
    // Todos os usuários tipo administrador tem acesso a esta lista.
    public ResponseEntity<Page<Servico>> listarServicosAtivos(@PageableDefault (sort = "descricao") Pageable paginacao) {
        Page<Servico> servicosAtivos = servicoService.listarServicosAtivos(paginacao);
        return ResponseEntity.ok(servicosAtivos);
    }

    @GetMapping("/listarServicosGerente")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<Page<Servico>> listagemServicosGerente(@PageableDefault (sort = "idServico") Pageable paginacao) {
        // O serviço retorna uma lista de todos os serviços, incluindo todas as informações.
        Page<Servico> servicos = servicoService.listagemServicosGerente(paginacao);
        return ResponseEntity.ok(servicos);
    }

    @PutMapping("/editarServico")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<DetalhamentoServicoDTO> editarServico(@Valid @RequestBody EditarServicosDTO dados) {
        // O serviço edita um serviço existente com base nos dados fornecidos.
        // Este endpoint está disponível para os usuários GERENTE e ROOT.
        Servico servicoEditado = servicoService.editarServicos(dados);
        DetalhamentoServicoDTO detalhamentoServicoDTO = new DetalhamentoServicoDTO(servicoEditado);
        return ResponseEntity.ok(detalhamentoServicoDTO);
    }

    @DeleteMapping("/excluirServico/{idServico}")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity excluirServico (@PathVariable Long idServico) {
        // O serviço marca o serviço com o ID fornecido como inativo.
        // Este endpoint está disponível apenas para os usuários GERENTE e ROOT.
        Servico servicoExcluir = servicoService.excluirServico(idServico);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/excluirServicoGerente/{idServico}")
    @Secured({"ROLE_ROOT"})
    public ResponseEntity excluirServicoGerente(@PathVariable Long idServico) {
        // O serviço exclui permanentemente o serviço com o ID fornecido do banco de dados.
        // Este endpoint está disponível apenas para o usuário ROOT.
        servicoService.excluirServicoGerente(idServico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalharServico/{idServico}")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity detalharServico (@PathVariable Long idServico) {
        // O serviço fornece os detalhes do serviço com o ID fornecido.
        // Este endpoint está disponível apenas para os usuários GERENTE e ROOT.
        Servico servicoDetalhar = servicoService.detalharServico(idServico);
        DetalhamentoServicoDTO detalhamentoServicoDTO = new DetalhamentoServicoDTO(servicoDetalhar);
        return ResponseEntity.ok(detalhamentoServicoDTO);
    }
    @GetMapping("/tipo-servico")
    public ResponseEntity<?> getTipoServico() {
        return ResponseEntity.ok(TipoServico.values());
    }
}
