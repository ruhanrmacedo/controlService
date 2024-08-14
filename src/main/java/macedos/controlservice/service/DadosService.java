package macedos.controlservice.service;

import macedos.controlservice.dto.servico.ListagemTecnicoDTO;
import macedos.controlservice.dto.servicoExecutado.ServicoExecutadoListagemDTO;
import macedos.controlservice.dto.tecnico.ListagemServicosDTO;
import macedos.controlservice.repository.ServicoExecutadoRepository;
import macedos.controlservice.repository.ServicoRepository;
import macedos.controlservice.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DadosService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ServicoExecutadoRepository servicoExecutadoRepository;

    public List<ListagemTecnicoDTO> getAllTecnicos() {
        return tecnicoRepository.findAll()
                .stream()
                .map(tecnico -> new ListagemTecnicoDTO(tecnico))
                .collect(Collectors.toList());
    }

    public List<ListagemServicosDTO> getAllServicos() {
        return servicoRepository.findAll()
                .stream()
                .map(servico -> new ListagemServicosDTO(servico))
                .collect(Collectors.toList());
    }

    public List<ServicoExecutadoListagemDTO> getAllServicosExecutados() {
        return servicoExecutadoRepository.findAll()
                .stream()
                .map(servicoExecutado -> new ServicoExecutadoListagemDTO(
                        servicoExecutado.getId(),
                        servicoExecutado.getContrato(),
                        servicoExecutado.getOs(),
                        servicoExecutado.getData(),
                        servicoExecutado.getTecnico().getNome(),
                        servicoExecutado.getServico().getDescricao(),
                        servicoExecutado.getServicosAdicionais().stream().map(servico -> servico.getDescricao()).collect(Collectors.toList()),
                        servicoExecutado.getServico().getValor1(),
                        servicoExecutado.getValorTotal()
                ))
                .collect(Collectors.toList());
    }
}
