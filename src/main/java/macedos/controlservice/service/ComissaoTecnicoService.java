package macedos.controlservice.service;

import macedos.controlservice.dto.comissaoTecnico.ContratoExecutadoDTO;
import macedos.controlservice.dto.comissaoTecnico.EvolucaoValorDTO;
import macedos.controlservice.dto.comissaoTecnico.ValoresExecutadosDTO;
import macedos.controlservice.entity.ServicoExecutado;
import macedos.controlservice.repository.ServicoExecutadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComissaoTecnicoService {

    @Autowired
    private ServicoExecutadoRepository servicoExecutadoRepository;

    //Calcula a comissão de um técnico para um mês e ano específicos.
    public BigDecimal calcularComissao(Long tecnicoId, int mes, int ano) {
        int numeroContratos = servicoExecutadoRepository.contarServicosPorTecnicoMesEAno(tecnicoId, mes, ano);
        BigDecimal valor1Total = servicoExecutadoRepository.somarValor1PorTecnicoMesEAno(tecnicoId, mes, ano);

        // Assegura que valor1Total não seja null
        valor1Total = valor1Total != null ? valor1Total : BigDecimal.ZERO;

        BigDecimal percentualComissao = calcularPercentualComissao(numeroContratos);
        return valor1Total.multiply(percentualComissao).setScale(2, RoundingMode.HALF_UP);
    }

    //Calcula o percentual de comissão com base no número de contratos.
    private BigDecimal calcularPercentualComissao(int contratos) {
        BigDecimal percentual;
        // Lógica para determinar o percentual com base no número de contratos
        if (contratos >= 100) {
            percentual = new BigDecimal("0.06"); // 6%
        } else if (contratos >= 90) {
            percentual = new BigDecimal("0.055"); // 5,5%
        } else if (contratos >= 80) {
            percentual = new BigDecimal("0.05"); // 5%
        } else if (contratos >= 70) {
            percentual = new BigDecimal("0.045"); // 4,5%
        } else if (contratos >= 50) {
            percentual = new BigDecimal("0.04"); // 4%
        } else {
            percentual = BigDecimal.ZERO;
        }

        return percentual;
    }

    public List<ContratoExecutadoDTO> buscarContratosExecutados(Long tecnicoId, int mes, int ano) {
        List<ServicoExecutado> servicos = servicoExecutadoRepository.encontrarPorTecnicoMesEAno(tecnicoId, mes, ano);
        int totalContratos = servicos.size(); // Obter o total de contratos para calcular o percentual de comissão

        return servicos.stream().map(servico -> {
            BigDecimal valor1 = new BigDecimal(servico.getServico().getValor1().toString());
            BigDecimal valorTotal = new BigDecimal(servico.getValorTotal().toString());
            BigDecimal comissao = calcularComissaoServico(valor1, totalContratos); // Calcular a comissão para cada serviço

            return new ContratoExecutadoDTO(
                    servico.getId(),
                    servico.getContrato(),
                    servico.getOs(),
                    servico.getData(),
                    servico.getTecnico().getNome(),
                    servico.getServico().getDescricao(),
                    servico.getServicosAdicionais().stream()
                            .map(servicoAdicional -> servicoAdicional.getDescricao())
                            .collect(Collectors.joining(", ")), // Converte a lista para uma string separada por vírgulas
                    valor1,
                    valorTotal,// Já convertido para BigDecimal
                    comissao // Comissão calculada individualmente para o serviço
            );
        }).collect(Collectors.toList());
    }

    public ValoresExecutadosDTO buscarValoresExecutados(Long tecnicoId, int mes, int ano) {
        BigDecimal valor1Total = servicoExecutadoRepository.somarValor1PorTecnicoMesEAno(tecnicoId, mes, ano);
        BigDecimal valorTotal = servicoExecutadoRepository.somarValorTotalPorTecnicoMesEAno(tecnicoId, mes, ano);
        return new ValoresExecutadosDTO(valor1Total, valorTotal);
    }

    // Método para buscar a evolução do valor 1 por mês
    public List<Object[]> buscarEvolucaoValor(Long tecnicoId) {
        LocalDate fim = LocalDate.now();
        LocalDate inicio = fim.minusMonths(6);
        return servicoExecutadoRepository.buscarEvolucaoValorMensalPorTecnico(tecnicoId, inicio, fim);
    }

    private BigDecimal calcularComissaoServico(BigDecimal valorServico, int totalContratos) {
        BigDecimal percentualComissao = calcularPercentualComissao(totalContratos);
        return valorServico.multiply(percentualComissao).setScale(2, RoundingMode.HALF_UP);
    }

    public List<Object[]> buscarEvolucaoContratos(Long tecnicoId) {
        LocalDate fim = LocalDate.now();
        LocalDate inicio = fim.minusMonths(6);
        return servicoExecutadoRepository.buscarEvoContExePorTecnico(tecnicoId, inicio, fim);
    }
}
