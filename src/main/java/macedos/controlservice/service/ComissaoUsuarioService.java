package macedos.controlservice.service;

import macedos.controlservice.repository.ComissaoTecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComissaoUsuarioService {

    @Autowired
    private ComissaoTecnicoRepository comissaoTecnicoRepository;


}
