package macedos.controlservice.controller;

import jakarta.validation.Valid;
import macedos.controlservice.tecnico.DadosCadastroTecnico;
import macedos.controlservice.tecnico.DadosListagemTecnico;
import macedos.controlservice.tecnico.Tecnico;
import macedos.controlservice.tecnico.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroTecnico dados) {

        repository.save(new Tecnico(dados));
    }

    @GetMapping
    public Page<DadosListagemTecnico> listar(Pageable paginacao) {
        return repository.findByDataDesligamentoIsNull(paginacao).map(DadosListagemTecnico::new);
    }
}
