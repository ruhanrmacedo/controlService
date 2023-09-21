package macedos.controlservice.service;

import jakarta.validation.Valid;
import macedos.controlservice.dto.tecnico.DemitirTecnicoDTO;
import macedos.controlservice.dto.tecnico.EditarTecnicosDTO;
import macedos.controlservice.entity.Tecnico;
import macedos.controlservice.repository.TecnicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public Tecnico cadastrarTecnico(@Valid Tecnico tecnico) {
        tecnicoRepository.save(tecnico);
        return tecnico;
    }

    public Page<Tecnico> listarTecnicosDTO(Pageable paginacao) {
        return tecnicoRepository.findByDataDesligamentoIsNull(paginacao);
    }

    public Page<Tecnico> listarTodosTecnicos(Pageable paginacao) {
        return tecnicoRepository.findAllByOrderByNome(paginacao);
    }

    public Tecnico editarTecnicos(EditarTecnicosDTO dados){
        var tecnico = tecnicoRepository.getReferenceById(dados.idTecnico());
        tecnico.atualizarInformacoes(dados);
        return tecnico;
    }

    public Tecnico demitirTecnico(DemitirTecnicoDTO dados) {
        var tecnicoDesligado = tecnicoRepository.getReferenceById(dados.idTecnico());
        tecnicoDesligado.desligarTecnico(dados);
        return tecnicoDesligado;
    }

    public void excluirTecnicoGerente(Long idTecnico){
        tecnicoRepository.deleteById(idTecnico);
    }

    public Tecnico detalharTecnico (Long idTecnico) {
        var tecnicoDetalhar = tecnicoRepository.getReferenceById(idTecnico);
        return tecnicoDetalhar;
    }
}
