package macedos.controlservice.service;

import jakarta.validation.Valid;
import macedos.controlservice.dto.EditarServicosDTO;
import macedos.controlservice.dto.ListagemServicosDTO;
import macedos.controlservice.entity.Servico;
import macedos.controlservice.repository.ServicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ServicoService.class); // Defina o logger


    @Autowired
    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Servico cadastrarServico(@Valid Servico servico) {
        servicoRepository.save(servico);
        return servico;
    }

    public Page<Servico> listarServicosAtivos(Pageable paginacao) {
        return servicoRepository.findByAtivoTrue(paginacao);
    }

    public List<Servico> listagemServicosGerente() {
        return servicoRepository.findAll();
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
}