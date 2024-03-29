package macedos.controlservice.service;

import jakarta.validation.Valid;
import macedos.controlservice.dto.servico.EditarServicosDTO;
import macedos.controlservice.entity.Servico;
import macedos.controlservice.repository.ServicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ServicoService.class); // Defina o logger

    public Servico cadastrarServico(@Valid Servico servico) {
        servicoRepository.save(servico);
        return servico;
    }

    public Page<Servico> listarServicosAtivos(Pageable paginacao) {
        return servicoRepository.findByAtivoTrue(paginacao);
    }

    public Page<Servico> listagemServicosGerente(Pageable paginacao) {
        return servicoRepository.findAllByOrderByDescricao(paginacao);
    }

    public Servico editarServicos(EditarServicosDTO dados) {
        var servico = servicoRepository.getReferenceById(dados.idServico());
        servico.atualizarInformacoes(dados);
        return servico;
    }

    public Servico excluirServico(Long idServico){
        var servicoExcluir = servicoRepository.getReferenceById(idServico);
        servicoExcluir.excluir();

        return servicoExcluir;
    }

    public void excluirServicoGerente(Long idServico){
        servicoRepository.deleteById(idServico);
    }

    public Servico detalharServico (Long idServico) {
        var servicoDetalhar = servicoRepository.getReferenceById(idServico);
        return servicoDetalhar;
    }

    public Optional<Servico> buscarServicoPorId(Long idServico){
        var findServico = servicoRepository.findById(idServico);
        return findServico;
    }
}
