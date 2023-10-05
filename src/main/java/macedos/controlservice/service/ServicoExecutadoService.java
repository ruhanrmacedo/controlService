package macedos.controlservice.service;

import macedos.controlservice.dto.servicoExecutado.RegistrarServicoDTO;
import macedos.controlservice.entity.ServicoExecutado;
import macedos.controlservice.exception.ValidacaoException;
import macedos.controlservice.repository.ServicoExecutadoRepository;
import macedos.controlservice.repository.ServicoRepository;
import macedos.controlservice.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
