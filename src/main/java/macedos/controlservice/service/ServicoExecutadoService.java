package macedos.controlservice.service;

import macedos.controlservice.dto.servicoExecutado.RegistrarServicoDTO;
import macedos.controlservice.dto.servicoExecutado.ServicoExecutadoListagemDTO;
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

@Service
public class ServicoExecutadoService {

    @Autowired
    private ServicoExecutadoRepository servicoExecutadoRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ServicoRepository servicoRepository;

    public void registrarServico(RegistrarServicoDTO dados) {
        if (!tecnicoRepository.existsById(dados.idTecnico())) {
            throw new ValidacaoException("Id do técnico informado não existe!");
        }
        if (!servicoRepository.existsById(dados.idServico())) {
            throw new ValidacaoException("Id do técnico informado não existe!");
        }

        var tecnico = tecnicoRepository.getReferenceById(dados.idTecnico());
        var servico = servicoRepository.getReferenceById(dados.idServico());

        var servicoExecutado = new ServicoExecutado(null, dados.contrato(), dados.os(), dados.data(), tecnico, servico);
        servicoExecutadoRepository.save(servicoExecutado);
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

}
