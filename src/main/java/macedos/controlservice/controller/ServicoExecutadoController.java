package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import macedos.controlservice.dto.ResumoMensalAdmDTO;
import macedos.controlservice.dto.servicoExecutado.*;
import macedos.controlservice.dto.tecnico.ListagemServicosDTO;
import macedos.controlservice.entity.Servico;
import macedos.controlservice.entity.ServicoExecutado;
import macedos.controlservice.service.ServicoExecutadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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


    @GetMapping("/listarServicosExecutadosAdm")
    // Todos os usuários tipo administrador tem acesso a esta lista.
    public Page<ListagemServExecutadosAdmDTO> listarServExecuAdm(@RequestParam int page, @RequestParam int size) {
        Pageable paginacao = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ServicoExecutado> servicosExecutadosAdm = servicoExecutadoService.listarServExecuAdm(paginacao);
        Page<ListagemServExecutadosAdmDTO> servicosDTO = servicosExecutadosAdm.map(servicoExecutado -> new ListagemServExecutadosAdmDTO(
                // Chama o construtor da DTO passando os valores corretos
                servicoExecutado.getId(),
                servicoExecutado.getContrato(),
                servicoExecutado.getOs(),
                servicoExecutado.getData(),
                servicoExecutado.getTecnico().getNome(),
                servicoExecutado.getServico().getDescricao(),
                servicoExecutado.getServicosAdicionais().stream()
                        .map(Servico::getDescricao)
                        .collect(Collectors.joining(", ")) // Converte a lista para uma string separada por vírgulas
        ));

        return servicosDTO;
    }

    @GetMapping("/listarServicosExecutados")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    // Usuários tipo GERENTE e ROOT tem acesso a esta lista.
    public ResponseEntity<?> listagemServicoExecutado(@RequestParam int page, @RequestParam int size) {
        Pageable paginacao = PageRequest.of(page, size, Sort.by("id").descending());
        try {
            Page<ServicoExecutadoListagemDTO> servicosExecutadosDTO = servicoExecutadoService.listagemServicoExecutado(paginacao);
            return ResponseEntity.ok(servicosExecutadosDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a sua requisição: " + e.getMessage());
        }
    }

    @GetMapping("/resumoMensal")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<?> obterResumoMensal(
            @RequestParam int mes,
            @RequestParam int ano) {
        try {
            ResumoMensalServicoDTO resumo = servicoExecutadoService.calcularResumoMensal(mes, ano);
            return ResponseEntity.ok(resumo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a sua requisição: " + e.getMessage());
        }
    }

    @GetMapping("/resumoQuinzenal")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<?> obterResumoQuinzenal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        try {
            ResumoMensalServicoDTO resumo = servicoExecutadoService.calcularResumoQuinzenal(dataInicio, dataFim);
            return ResponseEntity.ok(resumo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a sua requisição: " + e.getMessage());
        }
    }

    @GetMapping("/calcularResumoMensalAdm")
    public ResponseEntity<?> calcularResumoMensalUsuarioPadrao(
            @RequestParam int mes,
            @RequestParam int ano) {
        try {
            List<ServicoExecutado> servicosDoMes = servicoExecutadoService.calcularServicosDoAdmPorMesEAno(mes, ano);
            int quantidadeServicos = servicosDoMes.size();

            // Substitua 'ResumoMensalUsuarioPadraoDTO' por um DTO que inclui apenas a contagem
            ResumoMensalAdmDTO resumo = new ResumoMensalAdmDTO(quantidadeServicos);
            return ResponseEntity.ok(resumo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a sua requisição: " + e.getMessage());
        }
    }

    @GetMapping("/listarPorMesEAno")
    //Endpoint para listar os serviços registrados dentro do mês/ano solicitado
    public ResponseEntity<?> listarServicosPorMesEAno(
            @RequestParam int mes,
            @RequestParam int ano) {
        try {
            List<ServicoExecutadoListagemDTO> servicosDoMes = servicoExecutadoService.listarServicosPorMesEAno(mes, ano);
            return ResponseEntity.ok(servicosDoMes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a sua requisição: " + e.getMessage());
        }
    }

    //Endpoint para buscar serviços executados dentro de um intervalo de datas (quinzenal).
    @GetMapping("/listarPorPeriodo")
    public ResponseEntity<?> listarServicosPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        try {
            List<ServicoExecutadoListagemDTO> servicosDoPeriodo = servicoExecutadoService.listarServicosPorPeriodo(dataInicio, dataFim);
            return ResponseEntity.ok(servicosDoPeriodo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a sua requisição: " + e.getMessage());
        }
    }


    @GetMapping("/listarServicosAdmPorMesEAno")
    public ResponseEntity<?> listarServicosAdmPorMesEAno(
            @RequestParam int mes,
            @RequestParam int ano) {
        try {
            List<ServicoExecutadoAdmListagemDTO> servicosAdmListagem = servicoExecutadoService.listarServicosDoAdmPorMesEAno(mes, ano);
            return ResponseEntity.ok(servicosAdmListagem);
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
