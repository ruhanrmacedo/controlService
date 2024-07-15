package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import macedos.controlservice.dto.comissaoTecnico.ContratoExecutadoDTO;
import macedos.controlservice.dto.comissaoTecnico.EvolucaoValorDTO;
import macedos.controlservice.dto.comissaoTecnico.ValoresExecutadosDTO;
import macedos.controlservice.service.ComissaoTecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/comissaoTecnico")
@SecurityRequirement(name = "bearer-key")
public class ComissaoTecnicoController {

    @Autowired
    ComissaoTecnicoService comissaoTecnicoService;

    @GetMapping("/calcular")
    public ResponseEntity<BigDecimal> calcularComissao(
            @RequestParam Long tecnicoId,
            @RequestParam int mes,
            @RequestParam int ano) {

        BigDecimal comissaoCalculada = comissaoTecnicoService.calcularComissao(tecnicoId, mes, ano);
        return ResponseEntity.ok(comissaoCalculada);
    }

    @GetMapping("/contratos-executados")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<List<ContratoExecutadoDTO>> getContratosExecutados(
            @RequestParam Long tecnicoId,
            @RequestParam int mes,
            @RequestParam int ano) {
        List<ContratoExecutadoDTO> contratosExecutados = comissaoTecnicoService.buscarContratosExecutados(tecnicoId, mes, ano);
        return ResponseEntity.ok(contratosExecutados);
    }

    @GetMapping("/valores-executados")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<ValoresExecutadosDTO> getValoresExecutados(
            @RequestParam Long tecnicoId,
            @RequestParam int mes,
            @RequestParam int ano) {
        ValoresExecutadosDTO valoresExecutados = comissaoTecnicoService.buscarValoresExecutados(tecnicoId, mes, ano);
        return ResponseEntity.ok(valoresExecutados);
    }

    @GetMapping("/evolucao-valor")
    @Secured({"ROLE_GERENTE", "ROLE_ROOT"})
    public ResponseEntity<List<Object[]>> getEvolucaoValor(@RequestParam Long tecnicoId) {
        List<Object[]> evolucaoValor = comissaoTecnicoService.buscarEvolucaoValor(tecnicoId);
        return ResponseEntity.ok(evolucaoValor);
    }

    @GetMapping("/evolucao-contratos")
    public ResponseEntity<List<Object[]>> getEvolucaoContratos(@RequestParam Long tecnicoId) {
        List<Object[]> evolucaoContratos = comissaoTecnicoService.buscarEvolucaoContratos(tecnicoId);
        return ResponseEntity.ok(evolucaoContratos);
    }

}
