package macedos.controlservice.controller;

import jakarta.validation.Valid;
import macedos.controlservice.dto.CadastroTecnicoDTO;
import macedos.controlservice.dto.ListagemTecnicoDTO;
import macedos.controlservice.entity.Tecnico;
import macedos.controlservice.service.TecnicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService =  tecnicoService;
    }

    @PostMapping("/cadastrarTecnico")
    @Transactional
    public ResponseEntity<String> cadastrar(@Valid @RequestBody CadastroTecnicoDTO cadastroTecnicoDTO) {
        try {
            Tecnico tecnico = new Tecnico(cadastroTecnicoDTO);
            tecnicoService.cadastrarTecnico(tecnico);
            return new ResponseEntity<>("Técnico cadastrado com sucesso!", HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>("Erro ao tentar cadastrar novo técnico: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listarTecnicos")
    public ResponseEntity<List<ListagemTecnicoDTO>> listar() {
        try {
            List<ListagemTecnicoDTO> tecnicos = tecnicoService.listarTecnicosDTO();
            return new ResponseEntity<>(tecnicos, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
}
