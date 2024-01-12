package macedos.controlservice.service;

import macedos.controlservice.repository.IntervaloComissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntervaloComissaoService {

    @Autowired
    private IntervaloComissaoRepository intervaloComissaoRepository;
}
