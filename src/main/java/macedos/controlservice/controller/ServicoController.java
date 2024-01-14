package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import macedos.controlservice.dto.servico.CadastroServicoDTO;
import macedos.controlservice.dto.servico.DetalhamentoServicoDTO;
import macedos.controlservice.dto.servico.EditarServicosDTO;
import macedos.controlservice.dto.tecnico.ListagemServicosDTO;
import macedos.controlservice.entity.Servico;
import macedos.controlservice.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * Endpoint para cadastrar um novo serviço, disponível apenas para os usuários GERENTE e ROOT.
     *
     * @param cadastroServicoDTO Os dados para cadastrar um novo serviço.
     * @param uriBuilder O construtor de URIs para criar a URI de resposta.
     * @return ResponseEntity contendo os detalhes do serviço cadastrado.
     * Este endpoint está disponível apenas para os usuários GERENTE e ROOT.
     */
    @PostMapping("/cadastrarServico")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<DetalhamentoServicoDTO> cadastrar(@Valid @RequestBody CadastroServicoDTO cadastroServicoDTO, UriComponentsBuilder uriBuilder){
        Servico servico = new Servico(cadastroServicoDTO);
            servicoService.cadastrarServico(servico);
            var uri = uriBuilder.path("api/servicos/cadastrarServico/{idServico}").buildAndExpand(servico.getIdServico()).toUri();
            return ResponseEntity.created(uri).body(new DetalhamentoServicoDTO(servico));
    }

    /**
     * Endpoint para listar serviços disponíveis para os usuários ADMINISTRADOR, GERENTE E ROOT.
     *
     * @param paginacao A configuração de paginação.
     * @return ResponseEntity contendo uma página de serviços disponíveis para o ADMINISTRADOR,
     * com informações limitadas (id, descrição e tipo de serviço) em formato DTO (ListagemServicosDTO).
     */
    @GetMapping("/listarServicos")
    public ResponseEntity<Page<ListagemServicosDTO>> listar(Pageable paginacao) {
        // O serviço servicosAtivos contém apenas informações limitadas (id, descrição e tipo de serviço).
        // Todos os usuários tem acesso a esta lista.
        Page<Servico> servicosAtivos = servicoService.listarServicosAtivos(paginacao);
        Page<ListagemServicosDTO> servicosDTO = servicosAtivos.map(ListagemServicosDTO::new);

        return ResponseEntity.ok(servicosDTO);
    }

    /**
     * Endpoint para listar todos os serviços disponíveis para os usuários GERENTE e ROOT.
     *
     * @return ResponseEntity contendo uma lista de todos os serviços disponíveis,
     * incluindo todas as informações do serviço.
     */
    @GetMapping("/listarServicosGerente")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<List<Servico>> listagemServicosGerente() {
        // O serviço retorna uma lista de todos os serviços, incluindo todas as informações.
        // Este endpoint está disponível para os usuários GERENTE e ROOT.
        List<Servico> servicos = servicoService.listagemServicosGerente();
        return ResponseEntity.ok(servicos);
    }

    /**
     * Endpoint para editar um serviço existente, disponível apenas para os usuários GERENTE e ROOT..
     *
     * @param dados Os dados de edição do serviço.
     * @return ResponseEntity contendo os detalhes do serviço editado.
     */
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

    /**
     * Endpoint para marcar um serviço como inativo, disponível apenas para os usuários GERENTE e ROOT.
     * O serviço continua no banco de dados com a flag "ativo" alterada para false.
     *
     * @param idServico O ID do serviço a ser marcado como inativo.
     * @return ResponseEntity indicando o sucesso da operação.
     */
    @DeleteMapping("/excluirServico/{idServico}")
    @Transactional
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity excluirServico (@PathVariable Long idServico) {
        // O serviço marca o serviço com o ID fornecido como inativo.
        // Este endpoint está disponível apenas para os usuários GERENTE e ROOT.
        Servico servicoExcluir = servicoService.excluirServico(idServico);
        return ResponseEntity.noContent().build();
    }


    /**
     * Endpoint para excluir permanentemente um serviço do banco de dados, disponível apenas para o usuário ROOT.
     *
     * @param idServico O ID do serviço a ser excluído permanentemente.
     * @return ResponseEntity indicando o sucesso da exclusão permanente.
     */
    @DeleteMapping("/excluirServicoGerente/{idServico}")
    @Secured({"ROLE_ROOT"})
    public ResponseEntity excluirServicoGerente(@PathVariable Long idServico) {
        // O serviço exclui permanentemente o serviço com o ID fornecido do banco de dados.
        // Este endpoint está disponível apenas para o usuário ROOT.
        servicoService.excluirServicoGerente(idServico);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para detalhar informações de um serviço, disponível apenas para os usuários GERENTE e ROOT.
     *
     * @param idServico O ID do serviço a ser detalhado.
     * @return ResponseEntity contendo os detalhes do serviço.
     */
    @GetMapping("/detalharServico/{idServico}")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity detalharServico (@PathVariable Long idServico) {
        // O serviço fornece os detalhes do serviço com o ID fornecido.
        // Este endpoint está disponível apenas para os usuários GERENTE e ROOT.
        Servico servicoDetalhar = servicoService.detalharServico(idServico);
        DetalhamentoServicoDTO detalhamentoServicoDTO = new DetalhamentoServicoDTO(servicoDetalhar);
        return ResponseEntity.ok(detalhamentoServicoDTO);
    }
}
