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
    public BigDecimal calcularComissao(Long tecnicoId, int mes, int ano, boolean bonus) {
        int numeroContratos = servicoExecutadoRepository.contarServicosPorTecnicoMesEAno(tecnicoId, mes, ano);
        BigDecimal valor2Total = servicoExecutadoRepository.somarValor2PorTecnicoMesEAno(tecnicoId, mes, ano);

        // Assegura que valor2Total não seja null
        valor2Total = valor2Total != null ? valor2Total : BigDecimal.ZERO;

        BigDecimal percentualComissao = calcularPercentualComissao(numeroContratos, bonus);
        return valor2Total.multiply(percentualComissao).setScale(2, RoundingMode.HALF_UP);
    }

    //Calcula o percentual de comissão com base no número de contratos.
    private BigDecimal calcularPercentualComissao(int contratos, boolean bonus) {
        BigDecimal percentual;
        // Lógica para determinar o percentual com base no número de contratos
        if (contratos >= 90) {
            percentual = new BigDecimal("0.035"); // 3,5%
        } else if (contratos >= 80) {
            percentual = new BigDecimal("0.03"); // 3%
        } else if (contratos >= 70) {
            percentual = new BigDecimal("0.025"); // 2,5%
        } else if (contratos >= 60) {
            percentual = new BigDecimal("0.02"); // 2%
        } else {
            percentual = BigDecimal.ZERO;
        }
        // Aplicando bônus, se houver
        if (bonus) {
            percentual = percentual.add(new BigDecimal("0.02")); // Bônus de 2%
        }
        return percentual;
    }

    public List<ContratoExecutadoDTO> buscarContratosExecutados(Long tecnicoId, int mes, int ano, boolean bonus) {
        List<ServicoExecutado> servicos = servicoExecutadoRepository.encontrarPorTecnicoMesEAno(tecnicoId, mes, ano);
        int totalContratos = servicos.size(); // Obter o total de contratos para calcular o percentual de comissão

        return servicos.stream().map(servico -> {
            BigDecimal valor2 = new BigDecimal(servico.getServico().getValor2().toString());
            BigDecimal comissao = calcularComissaoServico(valor2, bonus, totalContratos); // Calcular a comissão para cada serviço

            return new ContratoExecutadoDTO(
                    servico.getId(),
                    servico.getContrato(),
                    servico.getOs(),
                    servico.getData(),
                    servico.getTecnico().getNome(),
                    servico.getServico().getDescricao(),
                    new BigDecimal(servico.getServico().getValor1().toString()),
                    valor2, // Já convertido para BigDecimal
                    comissao // Comissão calculada individualmente para o serviço
            );
        }).collect(Collectors.toList());
    }

    public ValoresExecutadosDTO buscarValoresExecutados(Long tecnicoId, int mes, int ano) {
        BigDecimal valor2Total = servicoExecutadoRepository.somarValor2PorTecnicoMesEAno(tecnicoId, mes, ano);
        BigDecimal valor1Total = servicoExecutadoRepository.somarValor1PorTecnicoMesEAno(tecnicoId, mes, ano);
        return new ValoresExecutadosDTO(valor2Total, valor1Total);
    }

    // Método para buscar a evolução do valor 1 por mês
    public List<Object[]> buscarEvolucaoValor(Long tecnicoId) {
        LocalDate fim = LocalDate.now();
        LocalDate inicio = fim.minusMonths(6);
        return servicoExecutadoRepository.buscarEvolucaoValorMensalPorTecnico(tecnicoId, inicio, fim);
    }

    private BigDecimal calcularComissaoServico(BigDecimal valorServico, boolean bonus, int totalContratos) {
        BigDecimal percentualComissao = calcularPercentualComissao(totalContratos, bonus);
        return valorServico.multiply(percentualComissao).setScale(2, RoundingMode.HALF_UP);
    }

    public List<Object[]> buscarEvolucaoContratos(Long tecnicoId) {
        LocalDate fim = LocalDate.now();
        LocalDate inicio = fim.minusMonths(6);
        return servicoExecutadoRepository.buscarEvoContExePorTecnico(tecnicoId, inicio, fim);
    }
}
