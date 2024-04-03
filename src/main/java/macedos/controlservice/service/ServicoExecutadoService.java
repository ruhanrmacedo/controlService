package macedos.controlservice.service;

import macedos.controlservice.dto.servicoExecutado.EditarServicoExecutadoDTO;
import macedos.controlservice.dto.servicoExecutado.RegistrarServicoDTO;
import macedos.controlservice.dto.servicoExecutado.ResumoMensalServicoDTO;
import macedos.controlservice.dto.servicoExecutado.ServicoExecutadoListagemDTO;
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

        ServicoExecutado servicoExecutado = new ServicoExecutado(null, dados.contrato(), dados.os(), dados.data(), tecnico, servico);
        return servicoExecutadoRepository.save(servicoExecutado);
    }

    public Page<ServicoExecutadoListagemDTO> listagemServicoExecutado(Pageable paginacao) {
        Page<ServicoExecutado> servicoExecutados = servicoExecutadoRepository.findAllByOrderByData(paginacao);
        return servicoExecutados.map(this::converterParaListagemDTO);
    }

    private ServicoExecutadoListagemDTO converterParaListagemDTO(ServicoExecutado servicoExecutado) {
        // Lógica para buscar os nomes e descrições
        String nomeTecnico = servicoExecutado.getTecnico().getNome();
        String descricaoServico = servicoExecutado.getServico().getDescricao();
        Double valorClaro = servicoExecutado.getServico().getValorClaro();
        Double valorMacedo = servicoExecutado.getServico().getValorMacedo();

        return new ServicoExecutadoListagemDTO(
                servicoExecutado.getId(),
                servicoExecutado.getContrato(),
                servicoExecutado.getOs(),
                servicoExecutado.getData(),
                nomeTecnico,
                descricaoServico,
                valorClaro,
                valorMacedo
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

    public Double calcularValorClaroPorMesEAno(int mes, int ano) {
        // Encontrar todos os serviços executados no mês/ano especificado
        List<ServicoExecutado> servicosDoMes = servicoExecutadoRepository.findAll().stream()
                .filter(servico -> isMesmoMesEAno(servico.getData(), mes, ano))
                .collect(Collectors.toList());

        // Calcular o valorClaro total para esses serviços
        Double valorTotalClaro = servicosDoMes.stream()
                .mapToDouble(this::calcularValorClaroIndividual)
                .sum();

        return valorTotalClaro;
    }

    // Verifica se a data do serviço é no mês e ano especificados
    private boolean isMesmoMesEAno(LocalDate data, int mes, int ano) {
        return data.getMonthValue() == mes && data.getYear() == ano;
    }

    // Método para calcular o valorClaro de um único serviço executado
    private Double calcularValorClaroIndividual(ServicoExecutado servicoExecutado) {
        // Aqui vai a lógica para calcular o valor claro de um serviço individual
        // Isso pode ser um valor fixo, um percentual do valor do serviço, etc.
        // Exemplo: valor base do serviço + 10%
        return servicoExecutado.getServico().getValorClaro() * 1;
    }

    private Double calcularValorMacedoIndividual(ServicoExecutado servicoExecutado) {
        // Aqui vai a lógica para calcular o valor claro de um serviço individual
        // Isso pode ser um valor fixo, um percentual do valor do serviço, etc.
        // Exemplo: valor base do serviço + 10%
        return servicoExecutado.getServico().getValorMacedo() * 1;
    }

    public ResumoMensalServicoDTO calcularResumoMensal(int mes, int ano) {
        List<ServicoExecutado> servicosDoMes = servicoExecutadoRepository.encontrarPorMesEAno(mes, ano);

        double valorTotalClaro = servicosDoMes.stream()
                .mapToDouble(this::calcularValorClaroIndividual)
                .sum();

        double valorTotalMacedos = servicosDoMes.stream()
                .mapToDouble(this::calcularValorMacedoIndividual)
                .sum();

        int quantidadeServicos = servicosDoMes.size();

        // Crie e retorne o DTO com as informações agregadas
        return new ResumoMensalServicoDTO(quantidadeServicos, valorTotalClaro, valorTotalMacedos);
    }
}
