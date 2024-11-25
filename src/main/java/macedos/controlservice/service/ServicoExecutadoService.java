package macedos.controlservice.service;

import macedos.controlservice.dto.servicoExecutado.*;
import macedos.controlservice.entity.Servico;
import macedos.controlservice.entity.ServicoExecutado;
import macedos.controlservice.infra.exception.ValidacaoException;
import macedos.controlservice.repository.ServicoExecutadoRepository;
import macedos.controlservice.repository.ServicoRepository;
import macedos.controlservice.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ServicoExecutadoService {

    @Autowired
    private ServicoExecutadoRepository servicoExecutadoRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ServicoRepository servicoRepository;

    public ServicoExecutado registrarServico(RegistrarServicoDTO dados) {
        if (!tecnicoRepository.existsById(dados.idTecnico())) {
            throw new ValidacaoException("Id do técnico informado não existe!");
        }
        if (!servicoRepository.existsById(dados.idServico())) {
            throw new ValidacaoException("Id do técnico informado não existe!");
        }

        var tecnico = tecnicoRepository.getReferenceById(dados.idTecnico());
        var servico = servicoRepository.getReferenceById(dados.idServico());

        // Verificar se o serviço está ativo
        if (!servico.isAtivo()) {
            throw new ValidacaoException("O serviço informado não está ativo!");
        }

        List<Servico> servicosAdicionais = dados.servicosAdicionais() != null ?
                dados.servicosAdicionais().stream()
                        .map(id -> servicoRepository.findById(id).orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()) : List.of();

        Double valorTotal = calcularValorTotal(
                servico.getValor1(),
                servicosAdicionais
        );

        ServicoExecutado servicoExecutado = new ServicoExecutado(
                null,
                dados.contrato(),
                dados.os(),
                dados.data(),
                tecnico,
                servico,
                servicosAdicionais,
                valorTotal,
                dados.nomeCliente(),
                dados.metragemCaboDrop()
        );
        return servicoExecutadoRepository.save(servicoExecutado);
    }

    private Double calcularValorTotal(Double valorPrincipal, List<Servico> servicosAdicionais) {
        Double valorTotalAdicionais = servicosAdicionais.stream().mapToDouble(Servico::getValor1).sum();
        Double valorSubtotal = valorPrincipal + valorTotalAdicionais;
        return valorSubtotal;
    }

    public Page<ServicoExecutado>  listarServExecuAdm(Pageable paginacao) {
        return servicoExecutadoRepository.findAllByOrderByIdDesc(paginacao);
    }
    public Page<ServicoExecutadoListagemDTO> listagemServicoExecutado(Pageable paginacao) {
        Page<ServicoExecutado> servicoExecutados = servicoExecutadoRepository.findAllByOrderByIdDesc(paginacao);
        return servicoExecutados.map(this::converterParaListagemDTO);
    }

    private ServicoExecutadoListagemDTO converterParaListagemDTO(ServicoExecutado servicoExecutado) {
        // Lógica para buscar os nomes e descrições
        String nomeTecnico = servicoExecutado.getTecnico().getNome();
        String descricaoServico = servicoExecutado.getServico().getDescricao();
        List<String> descricaoServicosAdicionais = servicoExecutado.getServicosAdicionais().stream()
                .map(Servico::getDescricao)
                .collect(Collectors.toList());
        Double valor1 = servicoExecutado.getServico().getValor1();
        Double valorTotal = servicoExecutado.getValorTotal();
        String nomeCliente = servicoExecutado.getNomeCliente();
        Double metragemCaboDrop = servicoExecutado.getMetragemCaboDrop();

        return new ServicoExecutadoListagemDTO(
                servicoExecutado.getId(),
                servicoExecutado.getContrato(),
                servicoExecutado.getOs(),
                servicoExecutado.getData(),
                nomeTecnico,
                descricaoServico,
                descricaoServicosAdicionais,
                valor1,
                valorTotal,
                nomeCliente,
                metragemCaboDrop
        );
    }

    public void excluirServicoExecutado(Long id){
        servicoExecutadoRepository.deleteById(id);
    }

    public ServicoExecutado editarServicoExecutado(EditarServicoExecutadoDTO dados) {
        var servicoExecutado = servicoExecutadoRepository.getReferenceById(dados.id());
        servicoExecutado.atualizarInformacoes(dados, tecnicoRepository, servicoRepository);
        return servicoExecutadoRepository.save(servicoExecutado);
    }

    public Double calcularValor1PorMesEAno(int mes, int ano) {
        // Encontrar todos os serviços executados no mês/ano especificado
        List<ServicoExecutado> servicosDoMes = servicoExecutadoRepository.findAll().stream()
                .filter(servico -> isMesmoMesEAno(servico.getData(), mes, ano))
                .collect(Collectors.toList());

        // Calcular o valor1 total para esses serviços
        Double valorTotal1 = servicosDoMes.stream()
                .mapToDouble(this::calcularValor1Individual)
                .sum();

        return valorTotal1;
    }

    public Double calcularValorTotalPorMesEAno(int mes, int ano) {
        // Encontrar todos os serviços executados no mês/ano especificado
        List<ServicoExecutado> servicosDoMes = servicoExecutadoRepository.findAll().stream()
                .filter(servico -> isMesmoMesEAno(servico.getData(), mes, ano))
                .collect(Collectors.toList());

        // Calcular o valor total para esses serviços
        Double valorTotal = servicosDoMes.stream()
                .mapToDouble(this::calcularValorTotalIndividual)
                .sum();

        return valorTotal;
    }

    // Verifica se a data do serviço é no mês e ano especificados
    private boolean isMesmoMesEAno(LocalDate data, int mes, int ano) {
        return data.getMonthValue() == mes && data.getYear() == ano;
    }

    // Método para calcular o valor1 de um único serviço executado
    private Double calcularValor1Individual(ServicoExecutado servicoExecutado) {
        return servicoExecutado.getServico().getValor1();
    }

    private Double calcularValorTotalIndividual(ServicoExecutado servicoExecutado) {
        return servicoExecutado.getValorTotal();
    }

    //Método responsável por calcular a quantidade de serviços, valor total 1 e somar valor total dentro do mês/ano solicitado pelo usuário
    public ResumoMensalServicoDTO calcularResumoMensal(int mes, int ano) {
        List<ServicoExecutado> servicosDoMes = servicoExecutadoRepository.encontrarPorMesEAno(mes, ano);

        double valorTotal1 = servicosDoMes.stream()
                .mapToDouble(this::calcularValor1Individual)
                .sum();

        double somaValorTotal = servicosDoMes.stream()
                .mapToDouble(this::calcularValorTotalIndividual)
                .sum();

        int quantidadeServicos = servicosDoMes.size();

        return new ResumoMensalServicoDTO(quantidadeServicos, valorTotal1, somaValorTotal);
    }

    // Método para calcular o resumo quinzenal usando o intervalo de datas
    public ResumoMensalServicoDTO calcularResumoQuinzenal(LocalDate dataInicio, LocalDate dataFim) {
        List<ServicoExecutado> servicosDoPeriodo = servicoExecutadoRepository.encontrarPorPeriodo(dataInicio, dataFim);

        double valorTota1 = servicosDoPeriodo.stream()
                .mapToDouble(this::calcularValor1Individual)
                .sum();

        double somaValorTotal = servicosDoPeriodo.stream()
                .mapToDouble(this::calcularValorTotalIndividual)
                .sum();

        int quantidadeServicos = servicosDoPeriodo.size();

        return new ResumoMensalServicoDTO(quantidadeServicos, valorTota1, somaValorTotal);
    }

    //Método listar os serviços registrados dentro do mês/ano solicitado
    public List<ServicoExecutadoListagemDTO> listarServicosPorMesEAno(int mes, int ano) {
        List<ServicoExecutado> servicosDoMes = servicoExecutadoRepository.encontrarPorMesEAno(mes, ano);
        return servicosDoMes.stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    //Método listar os serviços registrados dentro de um intervalo de datas (quinzenal)
    public List<ServicoExecutadoListagemDTO> listarServicosPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<ServicoExecutado> servicosDoPeriodo = servicoExecutadoRepository.encontrarPorPeriodo(dataInicio, dataFim);
        return servicosDoPeriodo.stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }


    public List<ServicoExecutado> calcularServicosDoAdmPorMesEAno(int mes, int ano) {
        // A implementação será similar ao método 'listarServicosPorMesEAno', mas retornando uma lista filtrada
        // adequada para usuários padrões, excluindo informações de valores.
        List<ServicoExecutado> servicosDoMes = servicoExecutadoRepository.encontrarPorMesEAno(mes, ano);
        return servicosDoMes; // Aqui você deve retornar a lista de ServicoExecutado diretamente
    }

    public List<ServicoExecutadoAdmListagemDTO> listarServicosDoAdmPorMesEAno(int mes, int ano) {
        List<ServicoExecutado> servicosDoMes = servicoExecutadoRepository.encontrarPorMesEAno(mes, ano);
        return servicosDoMes.stream()
                .map(servico -> new ServicoExecutadoAdmListagemDTO(
                        servico.getId(),
                        servico.getContrato(),
                        servico.getOs(),
                        servico.getData(),
                        servico.getTecnico().getNome(),
                        servico.getServico().getDescricao(),
                        servico.getServicosAdicionais().stream()
                                .map(Servico::getDescricao)
                                .collect(Collectors.joining(", "))
                ))
                .collect(Collectors.toList());
    }

}
