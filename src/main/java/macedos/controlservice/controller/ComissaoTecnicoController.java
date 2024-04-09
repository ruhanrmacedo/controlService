package macedos.controlservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import macedos.controlservice.dto.comissaoTecnico.ContratoExecutadoDTO;
import macedos.controlservice.dto.comissaoTecnico.EvolucaoValorDTO;
import macedos.controlservice.dto.comissaoTecnico.ValoresExecutadosDTO;
import macedos.controlservice.service.ComissaoTecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
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
            @RequestParam int ano,
            @RequestParam(defaultValue = "false") boolean bonus) {

        BigDecimal comissaoCalculada = comissaoTecnicoService.calcularComissao(tecnicoId, mes, ano, bonus);
        return ResponseEntity.ok(comissaoCalculada);
    }

    @GetMapping("/contratos-executados")
    public ResponseEntity<List<ContratoExecutadoDTO>> getContratosExecutados(
            @RequestParam Long tecnicoId,
            @RequestParam int mes,
            @RequestParam int ano,
            @RequestParam(defaultValue = "false") boolean bonus) {
        List<ContratoExecutadoDTO> contratosExecutados = comissaoTecnicoService.buscarContratosExecutados(tecnicoId, mes, ano, bonus);
        return ResponseEntity.ok(contratosExecutados);
    }

    @GetMapping("/valores-executados")
    public ResponseEntity<ValoresExecutadosDTO> getValoresExecutados(
            @RequestParam Long tecnicoId,
            @RequestParam int mes,
            @RequestParam int ano) {
        ValoresExecutadosDTO valoresExecutados = comissaoTecnicoService.buscarValoresExecutados(tecnicoId, mes, ano);
        return ResponseEntity.ok(valoresExecutados);
    }

    @GetMapping("/evolucao-valor")
    public ResponseEntity<List<EvolucaoValorDTO>> getEvolucaoValor(@RequestParam Long tecnicoId) {
        List<EvolucaoValorDTO> evolucaoValor = comissaoTecnicoService.buscarEvolucaoValor(tecnicoId);
        return ResponseEntity.ok(evolucaoValor);
    }
}
