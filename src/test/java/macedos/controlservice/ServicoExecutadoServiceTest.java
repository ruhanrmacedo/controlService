package macedos.controlservice;

import macedos.controlservice.entity.Servico;
import macedos.controlservice.entity.ServicoExecutado;
import macedos.controlservice.entity.Tecnico;
import macedos.controlservice.repository.ServicoExecutadoRepository;
import macedos.controlservice.service.ServicoExecutadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServicoExecutadoServiceTest {

    @Autowired
    private ServicoExecutadoService servicoExecutadoService;

    @Autowired
    private ServicoExecutadoRepository servicoExecutadoRepository;



    @Test
    public void calcularValor1PorMesEAno_deveRetornarValorCorreto() {
        // Action
        Double valorCalculado = servicoExecutadoService.calcularValor1PorMesEAno(2, 2024); // Substitua pelo mês/ano dos dados de teste

        // Assert
        Double valorEsperado = 100.0; // Substitua pelo valor claro total esperado com base nos dados de teste
        assertEquals(valorEsperado, valorCalculado, "O valor claro calculado deve ser igual ao valor esperado.");
    }
}

