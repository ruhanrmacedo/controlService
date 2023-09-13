package macedos.controlservice.service;

import jakarta.validation.Valid;
import macedos.controlservice.dto.ListagemTecnicoDTO;
import macedos.controlservice.entity.Tecnico;
import macedos.controlservice.repository.TecnicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    @Autowired
    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public Tecnico cadastrarTecnico(@Valid Tecnico tecnico) {
        tecnicoRepository.save(tecnico);
        return tecnico;
    }

    public List<ListagemTecnicoDTO> listarTecnicosDTO() {
        List<Tecnico> tecnicos = tecnicoRepository.findByDataDesligamentoIsNull();
        List<ListagemTecnicoDTO> tecnicosDTO = new ArrayList<>();
        for (Tecnico tecnico : tecnicos) {
            tecnicosDTO.add(new ListagemTecnicoDTO(tecnico));
        }
        return tecnicosDTO;
    }
}
